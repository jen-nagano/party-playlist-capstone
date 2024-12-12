<template>
  <div class="home">
    <div class="content">
      <h2 class="event-list-header">Events:</h2>
      <div v-if="events.length === 0">You are not hosting any events.</div>
      <!-- Event Form Section -->
      <div v-if="showEvent" class="event-form-section">
        <EventForm @formSubmitted="hideEvent" @cancel="hideEvent"/>
      </div>
      <!-- Event Tiles -->
      <div class="event-tiles-container">
        <div class="event-tiles">
          <!-- Add Event Button and Form -->
          <div>
            <button class="btn-create-event" v-on:click="showEvent = true">
              <span class="plus-sign">+</span> Add Event
            </button>
          </div>
          <div v-for="event in events" :key="event.id" class="event-tile">
            <h3 class="event-title">{{ event.name }}</h3>
            <p class="event-description">{{ event.description }}</p>
            <div class="event-buttons">
              <button class="btn-view-details" @click="viewEvent(event.id)">View Details</button>
              <button class="btn-view-details" @click="deleteEvent(event.id)">Remove Event</button>
            </div>
          </div>

        </div>
      </div>
        

      <h2 class="event-list-header">Playlists:</h2>
      <div v-if="savedPlaylists.length === 0">You do not have any saved playlists.</div>
    <!-- Create Playlist Form -->
    <div class="show_playlist" v-if="showPlaylist">
      <form v-on:submit.prevent="submitForm" class="playlistForm">
        <div class="form-group">
          <label for="playlist-name" class="form-label">Choose a name for your playlist:</label>
          <input
            id="playlist-name"
            type="text"
            class="form-input"
            v-model="editPlaylist.name"
            placeholder="Enter playlist name"
            autocomplete="off"
          />
        </div>
        <div class="form-actions">
          <button class="btn btn-cancel" v-on:click="cancelForm" type="button">Cancel</button>
          <button class="btn btn-submit">Create Playlist</button>
        </div>
      </form>
    </div>
    <!-- Create Playlist Form -->
    <div class="show_playlist" v-if="showPlaylist">
    </div>
      <div class="event-tiles">
        <div>
          <button class="btn-create-event" v-on:click="this.showPlaylist = true">
            <span class="plus-sign">+</span> Add Playlist
          </button>
        </div>
        <div v-for="playlist in savedPlaylists" :key="playlist.id" class="event-tile">
          <h3 class="event-title">{{ playlist.name }}</h3>
          <div class="event-buttons">
            <button class="btn-view-details" @click="viewPlaylist(playlist.playlistId)">View Details</button>
            <button class="btn-view-details" @click="removePlaylist(playlist.playlistId)">Remove Playlist</button>
          </div>
        </div>
      </div>


    </div>
  </div>
</template>
<script>
import EventForm from '../components/EventForm.vue';
import axios from 'axios';
import EventService from '../services/EventService';

export default {
  components: {
    EventForm
  },
  data() {
    return {
      showEvent: false,
      showPlaylist: false,
      events: [],
      guestEvents: [],
      savedPlaylists: [],
      editPlaylist: {
        name: '',
        userId: this.$store.state.user.id
      },
    };
  },
  created() {
    this.fetchEvents();
    this.fetchSavedPlaylists();
  },
  methods: {
    hideEvent() {
      this.showEvent = false;
      this.fetchEvents();
    },
    async fetchEvents() {
      try {
        const userId = this.$store.state.user?.id;
        if (!userId) {
          console.error('User ID is not defined');
          return;
        }
        const response = await axios.get(`/users/${userId}/events?role=host`);
        this.events = response.data;
      } catch (error) {
        console.error('Error fetching events:', error);
        this.events = [];
      }
    },
    async fetchGuestEvents() {
      try {
        const userId = this.$store.state.user?.id;
        if (!userId) {
          console.error('User ID is not defined');
          return;
        }
        const response = await axios.get(`/users/${userId}/events?role=guest`);
        this.guestEvents = response.data;
      } catch (error) {
        console.error('Error fetching events:', error);
        this.guestEvents = [];
      }
    },
    viewEvent(eventId) {
      this.$router.push({ name: "EventView", params: { eventId } });
    },
    viewPlaylist(playlistId) {
      this.$router.push({ name: 'PlaylistView', params: { playlistId: playlistId, eventId: 0 } });
    },
    async fetchSavedPlaylists() {
      try {
        const userId = this.$store.state.user?.id;
        if (!userId) {
          console.warn("User ID is not defined.");
          return;
        }
        const response = await axios.get(`/users/${userId}/playlists`);
        this.savedPlaylists = response.data;
      } catch (error) {
        if (error.response && error.response.status === 404) {
          console.warn("No saved playlists found for this user.");
        } else {
          console.error("Error fetching saved playlists:", error);
        }
      }
    },
    async deleteEvent(eventId) {
      var result = confirm("Are you sure you want to delete this event?");
      if (result) {
        const userId = this.$store.state.user?.id; // Make sure the user ID is available
        if (!userId) {
          console.warn("User ID is not defined.");
          return;
        }
        try {
          const response = await axios.delete(`/users/${userId}/events/${eventId}`);
          if (response.status === 204) {
            console.log('Event successfully deleted for the user.');
          } else {
            console.warn('Unexpected response status:', response.status);
          }
          this.fetchEvents();
        } catch (error) {
          console.error('Error deleting event for user:', error.response?.data || error.message);
          throw error; // Rethrow to handle higher-level errors if needed
        }
      }
    },
    async removePlaylist(playlistId) {
      var result = confirm("Are you sure you want to delete this playlist?");
      if (result) {
        //Logic to delete the item
        const userId = this.$store.state.user?.id; // Make sure the user ID is available
        if (!userId) {
          console.warn("User ID is not defined.");
          return;
        }
        try {
          const response = await axios.delete(`/users/${userId}/playlists/${playlistId}`);
          if (response.status === 204) {
            console.log('Playlist successfully deleted for the user.');
          } else {
            console.warn('Unexpected response status:', response.status);
          }
          this.fetchSavedPlaylists();
        } catch (error) {
          console.error('Error deleting playlist for user:', error.response?.data || error.message);
          throw error; // Rethrow to handle higher-level errors if needed
        }
      }

    },
    submitForm() {
      if (this.editPlaylist.name === '') {
        return;
      }
      EventService
        .addPlaylist(this.editPlaylist)
        .then(response => {
          if (response.status === 201) {
            console.log(response.data);
            this.savePlaylist(response.data.playlistId);
            this.$router.push({ name: 'PlaylistView', params: { playlistId: response.data.playlistId, eventId: 0 } });
          }
        })
        .catch(error => {
          console.error('Error adding playlist:', error);
        });
      this.showPlaylist = false;
    },
    savePlaylist(playlistId) {
      EventService
        .savePlaylist(this.$store.state.user.id, playlistId)
        .then(response => {
          if (response.status === 201) {
            console.log(response.data);
          }
        })
        .catch(error => {
          console.error('Error saving playlist:', error);
        });
    },
    cancelForm() {
      this.showPlaylist = false;
    },
  }
};
</script>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');
.home {
  font-family: 'Poppins', sans-serif;
  /*margin-top: 60px;*/
}
.banner {
  width: 100%;
  height: 350px;
  background-image: url('/src/img/home.png');
  background-size: cover;
  background-position: top center;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
  padding-top: 60px;
}
.banner-title {
  font-size: 3rem;
  font-weight: bold;
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
  color: #8E44AD;
}
.content {
  text-align: center;
  padding: 10px;
}
.welcome-message {
  font-size: 3rem;
  font-weight: bold;
  color: #8E44AD;
  margin-top: 20px;
  text-transform: uppercase;
  letter-spacing: 1px;
  text-shadow: 0 0 10px white;
}
.event-list-header {
  font-size: 2.5rem;
  color: white;
  margin-top: 20px;
  font-weight: bold;
}
.event-tiles-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.event-form-section {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}
.btn-create-event {
  display: inline-block;
  /*background-color: #9B59B6 ;*/
  background: linear-gradient(45deg, #9B59B6, #120916);
  color: white;
  padding: 20px 40px;
  border-radius: 10px;
  text-decoration: none;
  font-size: 1.8rem;
  text-align: center;
  margin-bottom: 30px;
  box-shadow: 0 8px 15px rgba(255, 255, 255, 0.959);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.btn-create-event:hover {
  transform: scale(1.05);
  box-shadow: 0 12px 20px rgba(0, 0, 0, 0.3);
}
.plus-sign {
  font-size: 3rem;
  margin-right: 10px;
  font-weight: bold;
}
.event-tiles {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}
.event-tile {
  background: linear-gradient(45deg, #9B59B6, #0A050C);
  color: white;
  padding: 30px;
  border-radius: 15px;
  text-align: center;
  width: 320px;
  height: 260px;
  box-shadow: 0 6px 12px rgb(255, 255, 255);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.event-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 10px;
  text-shadow: 0 0 10px white;
}
.event-description {
  font-size: 1.2rem;
  margin-bottom: 15px;
  text-shadow: 0 0 10px white;
}
.event-buttons {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.btn-view-details,
.btn-remove-event {
  background-color: rgb(14, 13, 13);
  color: #9B59B6;
  border: 2px solid #9B59B6;
  padding: 18px 28px; /* 45% bigger */
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}
.btn-view-details:hover {
  background-color: #8E44AD;
  color: white;
}
.btn-remove-event {
  background-color: #E74C3C;
  color: white;
  border: 2px solid #E74C3C;
}
.btn-remove-event:hover {
  background-color: #C0392B;
  color: white;
}
/* copied form stuff from event form */
/* Improved Playlist Form Styling */
.playlistForm {
  margin: 20px auto;
  padding: 20px;
  background: #333; /* Dark grey background */
  border-radius: 10px;
  width: 400px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

.form-label {
  display: block;
  font-size: 1.2rem;
  color: #fff; /* White text */
  margin-bottom: 10px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #aaa; /* Light grey border */
  font-size: 1rem;
  background: #222; /* Dark background */
  color: #fff; /* White text */
  box-sizing: border-box; /* Include padding and border in the width */
}

.form-input:focus {
  outline: none;
  border-color: #9B59B6; /* Purple border on focus */
  box-shadow: 0px 0px 8px rgba(155, 89, 182, 0.8); /* Purple shadow on focus */
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.btn {
  font-size: 1rem;
  padding: 0.7rem 1.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn:hover {
  background: #8e44ad; /* Darker purple on hover */
}

.btn-submit {
  background: #9B59B6; /* Purple background */
  color: #fff; /* White text */
}

.btn-submit:hover {
  background: #8e44ad; /* Darker purple for submit on hover */
}

.btn-cancel {
  background: #af7ac5; /* Lighter purple for cancel */
  color: #fff; /* White text */
}

.btn-cancel:hover {
  background: #9b59b6; /* Darker purple for cancel on hover */
}
</style>