<template>
    <div class="Event">
      <h1>Event</h1>
      <p>Complete the form to add a new event.</p>
      <EventForm />
      <!--<SearchSong /> -->
      <router-link class="btn btn-submit" :to="{ name: 'playlist' }">Create Playlist</router-link>
      
    </div>
  </template>
  
  <script>
  import EventForm from '../components/EventForm.vue';
  import SearchSong from '../components/SearchSong.vue';

  export default {
    name: 'EventView',
    components:{
      EventForm,
      //SearchSong
    }
  };
  </script>