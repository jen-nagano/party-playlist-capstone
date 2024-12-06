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
      </div>

      <!-- Message if no playlists -->
      <p v-if="playlists.length === 0">No playlists found for this event.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      event: {}, // Stores event details
      playlists: [] // Stores associated playlists
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
      alert(`View details for playlist ID: ${playlistId}`); // Replace with navigation logic 
    } 
  } 
}; </script>

<style scoped> 
    /* Style the layout to match HomeView */ 
    .playlist-tiles { display: flex; flex-wrap: wrap; gap: 20px; } 
    .playlist-tile { border: 1px solid #ddd; padding: 15px; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1); width: 200px; } 
    .playlist-tile h4 { margin: 0; } .playlist-tile p { font-size: 14px; color: #555; } 
  </style>