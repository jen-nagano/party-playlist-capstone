<template>
  <div class="home">
    <h1>Home</h1>
    <p>Welcome to the Panda Party!</p>
    <router-link class="btn btn-submit" :to="{ name: 'event' }">Create Event</router-link>
    
  </div>
</template>
<script>

export default {
  data() {
    return {

    };
  },
  methods: {
   
  }
};
</script>
<style scoped>
.home {
  padding: 20px;
}
.cardForm {
  margin-bottom: 20px;
}
</style>
