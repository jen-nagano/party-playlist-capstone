<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <router-link class="btn btn-submit" :to="{ name: 'event' }">Create Event</router-link>
    <p>{{ trackInfo.name }}</p>
    <img width="200" v-bind:src="coverImgUrl"/>
    <!--<p>By: {{ trackInfo.artists[0].name }}</p> -->
    <form v-on:submit.prevent="getTrack()" class="cardForm">
      <div class="form-group">
        <label for="track-id">Track Id:</label>
        <input id="track-id" type="text" class="form-control" v-model="trackId" autocomplete="off" />
      </div>
      <button class="btn btn-submit">Submit</button>
      <!--<button v-on:click="getTrack()">Get Track</button> -->
    </form>
  </div>
</template>

<script>
import SpotifyService from '../services/SpotifyService.js';

export default {

  components: {},
  data() {
    return {
      trackId:'',
      trackInfo: {},
      coverImgUrl:''
    }
  },
  methods: {
    getTrack() {
      SpotifyService.getTrack(this.trackId)
        .then(response => {
          this.trackInfo = response.data;
          this.coverImgUrl = this.trackInfo.album.images[0].url;
          console.log(this.trackInfo);
        })
    }
  }

};
</script>
