<template>
  <div class="landing-page" :class="backgroundClass">
    <div class="landing-content">
      <h1 class="landing-title">Welcome to Panda Party Playlist</h1>
      <p class="landing-description">
        Get ready to create your own playlist with the best music. Join the fun!
      </p>
      <router-link v-bind:to="{ name: 'home' }" class="start-button">Start</router-link>
    </div>
    <!-- Optional: Video background based on the route meta -->
    <video v-if="isVideoBackground" class="custom-video-background" autoplay loop muted>
      <source src="/src/video/4092801-hd_1920_1080_30fps.mp4 - Copy.crdownload" type="video/mp4">
      Your browser does not support the video tag.
    </video>
  </div>
</template>
≡My Ordinary LifeThe Living Tombstone3:51Remove Song
<script>
export default {
   computed: {
     // Dynamically change the background class based on route metadata
     backgroundClass() {
       return this.$route.meta.isCustomBackground ? 'custom-background' : 'default-background';
     },
    // Check if the background should be a video (based on route metadata)
    isVideoBackground() {
      return this.$route.meta.isCustomBackground && this.$route.meta.isVideoBackground;
    }
  }
};
</script>
<style scoped>
/* Landing Page default background styling */
.default-background {
  background: linear-gradient(to right, #0B0B0B, #181616); /* Default background */
  height: 100vh;
  color: #ECF0F1;
  position: relative;
}
/* Custom background image styling */
.custom-background {
  background: url('/path/to/your/landing-image.jpg') no-repeat center center fixed;
  background-size: cover;
  height: 100vh;
  position: relative;
  color: #ECF0F1;
}
/* Styling for video background */
.custom-video-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: -1; /* Ensure the video stays behind the content */
}
/* Landing page content */
.landing-content {
  position: relative;
  text-align: center;
  padding: 50px;
  z-index: 1;
}
.landing-title {
  font-size: 3rem;
  font-weight: bold;
  margin-bottom: 20px;
}
.landing-description {
  font-size: 1.5rem;
  margin-bottom: 30px;
}
.start-button {
  background-color: #741577;
  color: white;
  padding: 12px 25px;
  text-decoration: none;
  border-radius: 5px;
  font-size: 1.2rem;
  text-transform: uppercase;
  transition: background-color 0.3s ease;
}
.start-button:hover {
  background-color: #6A0E92;
}
</style>