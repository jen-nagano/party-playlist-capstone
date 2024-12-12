<template>
  <div class="main-container">
    <!-- Playlist Section -->
    <div class="playlist-container">
      <!-- Centered Playlist Name -->
      <h1 class="playlist-name">{{ playlist.name }}</h1>
      <!-- Centered Back to Home Button -->
      <button @click="navigateBack" class="back-button">Back to Home</button>
      <div class="songs-list mt-6">
        <h2 class="text-2xl font-semibold mb-4">Current Playlist Songs:</h2>
        <div ref="playlist" class="playlist">
          <div v-for="(song, index) in playlistSongs" :key="song.songId" class="song-card">
            <span class="drag-handle">≡</span>
            <img :src="song.imgUrl" alt="Album Art" />
            <div class="song-info">
              <p class="song-title">{{ song.title }}</p>
              <p class="song-artist">{{ song.artist }}</p>
              <p class="song-duration">{{ this.getDuration(song.duration) }}</p>
            </div>
            <button @click="removeSong(song.songId)">Remove Song</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Search Section -->
    <div class="search-section p-4 bg-gray-800 rounded-lg">
      <h1 class="text-center text-4xl font-bold py-4">Search for songs to add to your playlist.</h1>
      <div class="flex items-center space-x-2">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search"
          class="flex-grow px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <button
          @click="searchSongs"
          class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-all"
        >
          Search
        </button>
      </div>
      <div class="songs-list mt-6">
        <div v-for="song in songs" :key="song.id" class="song-card">
          <img :src="song.albumArt" alt="Album Art" />
          <div class="song-info">
            <p class="song-title">{{ song.title }}</p>
            <p class="song-artist">{{ song.artist }}</p>
          </div>
          <button @click="addSong(song)">Add to Playlist</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import EventService from "../services/EventService";
import Sortable from "sortablejs";
export default {
  props: {
    playlistId: {
      type: String,
      required: true,
    },
    eventId: {
      type: Number,
      required: true,
      default: 0,
    },
  },
  data() {
    return {
      playlist: {},
      searchQuery: "",
      songs: [],
      playlistSongs: [],
      playbackState: null,
      spotifyAccessToken: null,
    };
  },
  created() {
    this.fetchPlaylistDetails();
    this.fetchSongs();
  },
  mounted() {
    this.getSpotifyToken();
    Sortable.create(this.$refs.playlist, {
      animation: 150,
      handle: ".drag-handle",
      onEnd: this.updateOrder,
    });
  },
  methods: {
    async fetchPlaylistDetails() {
      try {
        const response = await axios.get(`/playlists/${this.playlistId}`);
        this.playlist = response.data;
      } catch (error) {
        console.error("Error fetching playlist details:", error);
        alert("Failed to load playlist details.");
      }
    },
    async fetchSongs() {
      try {
        const response = await axios.get(`/playlists/${this.playlistId}/songs`);
        this.playlistSongs = response.data;
      } catch (error) {
        if (error.response && error.response.status === 404) {
          console.warn("No songs found for this playlist.");
        } else {
          console.error("Error fetching songs:", error);
        }
      }
    },
    async fetchSpotifyToken() {
      try {
        const response = await axios.get("http://localhost:9000/spotify/token");
        this.spotifyAccessToken = response.data.access_token;
        const expiresIn = response.data.expires_in * 1000;
        localStorage.setItem("spotify_access_token", this.spotifyAccessToken);
        localStorage.setItem("spotify_token_expiry", Date.now() + expiresIn);
      } catch (error) {
        console.error("Error fetching Spotify token:", error);
      }
    },
    async getSpotifyToken() {
      const tokenExpiry = localStorage.getItem("spotify_token_expiry");
      if (!tokenExpiry || Date.now() > tokenExpiry) {
        await this.fetchSpotifyToken();
      } else {
        this.spotifyAccessToken = localStorage.getItem("spotify_access_token");
      }
    },
    async searchSongs() {
      await this.getSpotifyToken();
      if (!this.spotifyAccessToken) {
        console.error("Spotify token not available.");
        return;
      }
      try {
        const response = await axios.get("https://api.spotify.com/v1/search", {
          params: {
            q: this.searchQuery,
            type: "track",
            limit: 10,
          },
          headers: {
            Authorization: `Bearer ${this.spotifyAccessToken}`,
          },
        });
        this.songs = response.data.tracks.items.map((track) => ({
          id: track.id,
          title: track.name,
          artist: track.artists.map((artist) => artist.name).join(", "),
          albumArt: track.album.images[0]?.url,
          uri: track.uri,
          duration: track.duration_ms,
        }));
      } catch (error) {
        console.error("Error searching Spotify:", error);
      }
    },
    addSong(song) {
      const mySong = {
        title: song.title,
        artist: song.artist,
        duration: song.duration,
        spotifyId: song.uri.substring(14),
        imgUrl: song.albumArt,
      };
      EventService.addSongToPlaylist(mySong, this.playlist.playlistId).then(() => {
        this.fetchSongs();
      });
    },
    removeSong(songId) {
      axios.delete(`/playlists/${this.playlistId}/songs/${songId}`).then(() => {
        this.fetchSongs();
      }).catch((error) => {
        console.error("Error removing song from playlist:", error);
      });
    },
    getDuration(duration_ms) {
      let duration = Math.round(duration_ms / 1000);
      let minutes = Math.floor(duration / 60);
      let seconds = duration % 60;
      return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    },
    updateOrder(evt) {
      const movedItem = this.playlistSongs.splice(evt.oldIndex, 1)[0];
      this.playlistSongs.splice(evt.newIndex, 0, movedItem);
      this.saveOrder();
    },
    async saveOrder() {
      try {
        const requestBody = this.playlistSongs.map((song, index) => ({
          songId: song.songId,
          position: index + 1,
        }));
        await axios.put(`/playlists/${this.playlist.playlistId}/songs/order`, requestBody);
      } catch (error) {
        console.error("Error saving order:", error);
      }
    },
    navigateBack() {
      if (this.eventId == 0) {
        this.$router.push({ name: "home" });
      } else {
        this.$router.push({ name: "EventView", params: { playlistId: this.playlist.playlistId, eventId: this.eventId } });
      }
    },
  },
};
</script>
<style scoped>
.main-container {
  display: flex;
  gap: 20px;
  flex-wrap: wrap; /* Allow wrapping on smaller screens */
  justify-content: space-between;
}
.playlist-container {
  flex: 0 0 60%; /* 60% width for playlist section */
}
.playlist-name {
  text-align: center;
  margin: 0;
  padding-top: 5%;
}
.back-button {
  display: block;
  margin: 20px auto;
  padding: 10px 20px;
  font-size: 16px;
  background-color:rgba(255, 105, 180, 0.9);
  color: white;
  border: none;
  border-radius: 4px;
  text-align: center;
}
.back-button:hover {
  background-color: #f0ff1bf1;
  color: black;
}
.search-section {
  flex: 0 0 35%; /* 35% width for search section */
  background-color: #00000000;
  border-radius: 8px;
  padding: 16px;
}
.songs-list {
  margin-top: 20px;
}
.song-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 8px;
  margin-bottom: 8px;
  background-color: #070707;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(71, 241, 219, 0.808);
}
.song-card img {
  width: 100px;
  height: 100px;
  border-radius: 4px;
}
.song-card .song-info {
  flex: 1;
}
button {
  padding: 4px 8px;
  font-size: 14px;
  background-color: rgba(255, 105, 180, 0.9);
  color: rgb(255, 255, 255);
  border: none;
  border-radius: 4px;
  /* box-shadow: 0 4px 6px rgba(254, 255, 255, 0.808); */
}
button:hover {
  background-color: #f0ff1bf1;
  color: black;
}
@media (max-width: 768px) {
  .main-container {
    flex-direction: column;
  }
  .playlist-container, .search-section {
    flex: 0 0 100%;
  }
}
</style>

