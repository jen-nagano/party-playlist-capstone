<template>
    <div class="playlist">
      <h1>Playlist</h1>
      <p>Search for a song.</p>
      <SearchSong />
      
    </div>
  </template>
  
  <script>
  import SearchSong from '../components/SearchSong.vue';

  export default {
    name: 'PlaylistView',
    components:{
      SearchSong
    }
  };
  </script>