<template>
  <div>
    <h2>{{ event.name }}</h2>
    <p>{{ event.description }}</p>
    <p><strong>Date:</strong> {{ event.date }}</p>
    <p><strong>Location:</strong> {{ event.location }}</p>

    <h3>Playlists</h3>
    <div class="playlist-tiles">
      <!-- Show playlist tiles -->
      <div
        v-for="playlist in playlists"
        :key="playlist.playlistId"
        class="playlist-tile"
      >
        <h4>{{ playlist.name }}</h4>
        <p>{{ playlist.description }}</p>
        <button @click="viewPlaylist(playlist.playlistId)">View Playlist</button>
        <button @click="savePlaylist(playlist.playlistId)">Save Playlist</button>
      </div>

      <!-- Message if no playlists -->
      <p v-if="playlists.length === 0">No playlists found for this event.</p>

      <div>
        <!-- Create Event Button (Styled as a Panel with Plus Sign) -->
        <button class="btn-create-playlist" v-on:click="this.showPlaylist=true">
          <span class="plus-sign">+</span>
        </button>
      </div>

    </div>

    <div class="show_playlist" v-if="showPlaylist">
      <form v-on:submit.prevent="submitForm" class="playlistForm">
        <div class="form-group">
          <label for="playlist-name">Choose a name for your playlist:</label>
          <input id="playlist-name" type="text" class="form-control" v-model="editPlaylist.name" autocomplete="off" />
        </div>
        
        <button class="btn btn-submit">Create Playlist</button>
        <button class="btn btn-cancel" v-on:click="cancelForm" type="button">Cancel</button>
      </form>
      
    </div>

  </div>
</template>

<script>
import axios from 'axios';
import EventService from '../services/EventService';

export default {
  data() {
    return {
      event: {}, // Stores event details
      playlists: [], // Stores associated playlists
      showPlaylist: false,
      editPlaylist : {
        name: '',
        userId: this.$store.state.user.id
      }
    };
  },
  created() {
    this.fetchEventDetails();
    this.fetchPlaylists();
  },
  methods: {
    // Fetch details of the event
    async fetchEventDetails() {
      try {
        const eventId = this.$route.params.eventId;
        const response = await axios.get(`/events/${eventId}`);
        this.event = response.data;
      } catch (error) {
        console.error("Error fetching event details:", error);
        alert("Failed to load event details.");
      }
    },

    // Fetch playlists for the event
    async fetchPlaylists() {
      try {
        const eventId = this.$route.params.eventId;
        const response = await axios.get(`/events/${eventId}/playlists`);
        this.playlists = response.data;
      } catch (error) {
        if (error.response && error.response.status === 404) {
          console.warn("No playlists found for this event.");
        } else {
          console.error("Error fetching playlists:", error);
        }
      }
    },

    // Placeholder for viewing a playlist (if implemented later)
    viewPlaylist(playlistId) { 
      this.$router.push({ name: 'PlaylistView', params: { playlistId: playlistId } });
    },
    savePlaylist(playlistId) {
      EventService
        .savePlaylist(this.$store.state.user.id, playlistId)
        .then(response => {
          if (response.status === 201) {
            // this.$store.commit(
            //   'SET_NOTIFICATION',
            //   {
            //     message: 'A new event was added.',
            //     type: 'success'
            //   }
            // );
            //console.log(response.data);
            //this.$router.push({ name: 'PlaylistView', params: { playlistId: response.data.playlistId } });
          }
        })
        .catch(error => {
          console.log(error, 'adding');
        });
    },
    submitForm() {
      if (this.editPlaylist.name === '') {
        return;
      }

      console.log('creating a playlist named: '+ this.editPlaylist.name);

      EventService
        .addPlaylistToEvent(this.editPlaylist, this.$route.params.eventId)
        .then(response => {
          if (response.status === 201) {
            // this.$store.commit(
            //   'SET_NOTIFICATION',
            //   {
            //     message: 'A new event was added.',
            //     type: 'success'
            //   }
            // );
            console.log(response.data);
            this.$router.push({ name: 'PlaylistView', params: { playlistId: response.data.playlistId } });
          }
        })
        .catch(error => {
          console.log(error, 'adding');
        });
        this.showPlaylist = false;
    },
    cancelForm() {
      this.showPlaylist = false;
    }
  } 
}; </script>

<style scoped> 
  /* Style the layout to match HomeView */ 
  .playlist-tiles { 
    display: flex; 
    flex-wrap: wrap; 
    gap: 20px; 
  } 
  .playlist-tile { 
    border: 1px solid #ddd; 
    padding: 15px; 
    border-radius: 5px; 
    box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1); 
    width: 200px; 
  } 
  .playlist-tile h4 { 
    margin: 0; 
  } 
  .playlist-tile p { 
    font-size: 14px; 
    color: #555; 
  } 
  /* Button Styling for "Create Event" (Styled as a Panel with Plus Sign) */
  .btn-create-event {
    display: inline-block;
    background-color: #9b59b6;  /* Purple background color */
    color: white;
    padding: 60px 80px;  /* Increased padding to make the button 2x bigger */
    border-radius: 10px;
    text-decoration: none;
    font-size: 3rem;  /* Increased font size for larger button */
    text-align: center;
    margin-top: 30px;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    position: relative;
    animation: glowEffect 1.5s ease-in-out infinite;
  }


  .btn-create-event:hover {
    background-color: #8e44ad;  /* Darker purple on hover */
    transform: scale(1.1);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
  .plus-sign {
    font-size: 5rem;  /* Increased plus sign size for bigger button */
    margin-right: 10px;
    font-weight: bold;
    animation: bouncePlus 1s infinite;
  }
</style>