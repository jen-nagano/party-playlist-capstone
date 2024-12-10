<template>
  <div class="landing-page">
    <!-- Banner Section with Video -->
    <div class="banner">
      <!-- Video section with preloading -->
      <video class="banner-video" autoplay loop muted playsinline preload="metadata" @canplaythrough="handleVideoLoad">
        <source src="/src/video/4092801-hd_1920_1080_30fps.mp4 - Copy.crdownload" type="video/mp4">
        <!-- Fallback image for browsers that don't support video -->
        <img src="/src/images/video-placeholder.jpg" alt="Video is loading" class="video-placeholder">
        Your browser does not support the video tag.
      </video>
      <div v-if="isLoading" class="loading-spinner">
        <div class="spinner"></div>
      </div>
      <h1 class="banner-title">Welcome to Panda Party Playlist!</h1>
    </div>
    <!-- <h1 class="form-title">Welcome to Panda Party Playlist!</h1> -->
    <!-- Button Container -->
    <div class="landing-page-buttons">
      <button @click="goToSignUp" class="submit-button">Sign Up</button>
      <button @click="goToLogin" class="submit-button">Login</button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isLoading: true // Track video loading state
    };
  },
  methods: {
    goToSignUp() {
      this.$router.push({ name: 'register' });
    },
    goToLogin() {
      this.$router.push({ name: 'login' });
    },
    handleVideoLoad() {
      this.isLoading = false; // Hide loading spinner once the video has loaded
    }
  },
  mounted() {
    // Wait for the video to load and trigger the loading state change
    const video = document.querySelector('.banner-video');
    video.addEventListener('canplaythrough', this.handleVideoLoad, { once: true });
  }
};
</script>
<style scoped>
/* Center the content and apply black background */
.landing-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #000; /* Black background */
  color: #ECF0F1; /* Light text for contrast */
  font-family: 'Roboto', sans-serif;
  text-align: center;
  overflow: hidden;
}
/* Banner Section with Video */
.banner {
  width: 100%;
  height: 600px; /* Increased height for a bigger banner */
  position: relative;
  overflow: hidden;
}
/* Ensure the video fits properly */
.banner-video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
/* Placeholder image when the video is loading */
.video-placeholder {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
}
/* Loading spinner styling */
.loading-spinner {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
}
.spinner {
  border: 4px solid #F3F3F3; /* Light gray */
  border-top: 4px solid #3498DB; /* Blue */
  border-radius: 50%;
  width: 50px;
  height: 50px;
  animation: spin 2s linear infinite;
}
/* Spinner animation */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
.banner-title {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 4rem; /* Larger font size */
  font-weight: bold;
  text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.7); /* Enhanced text shadow for better readability */
  color: #F1EFF1;
  animation: pulseGlow 1.5s ease-in-out infinite;
}
/* Title Animation */
.form-title {
  font-size: 2.8rem; /* Slightly larger font size */
  color: #fff;
  margin-bottom: 50px;
  animation: bounce 2s infinite;
}
/* Button Container for vertical stack and centering */
.landing-page-buttons {
  display: flex;
  flex-direction: column;
  align-items: center; /* Center the buttons horizontally */
  gap: 20px; /* Space between buttons */
  width: 100%;
  max-width: 600px; /* Max width for buttons */
  margin-top: 20px; /* Added margin to give some space between buttons and the title */
}
/* Button Styling */
.submit-button {
  width: 70%; /* Adjust button width */
  padding: 15px;
  background: transparent;
  color: #fff;
  border: 2px solid #3498DB;
  border-radius: 50px;
  font-size: 1.2rem;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease, background 0.3s ease;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  animation: pulse 1.5s infinite;
}
/* Hover and active states */
.submit-button:hover {
  background: linear-gradient(45deg, #FF6B81, #3498DB);
  transform: scale(1.1);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
}
.submit-button:hover::before {
  transform: translate(-50%, -50%) scale(1);
}
.submit-button:active {
  transform: scale(1);
}
/* Button before element for hover animation */
.submit-button::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 300%;
  height: 300%;
  background: rgba(52, 152, 219, 0.5);
  transition: all 0.3s ease;
  border-radius: 50%;
  transform: translate(-50%, -50%) scale(0);
  z-index: -1;
}
/* Keyframe for the button pulse effect */
@keyframes pulse {
  0% {
    transform: scale(1);
    background-color: #3498DB;
  }
  50% {
    transform: scale(1.1);
    background-color: #FF6B81;
  }
  100% {
    transform: scale(1);
    background-color: #3498DB;
  }
}
/* Bounce Animation for the Title */
@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-15px);
  }
  60% {
    transform: translateY(-7px);
  }
}
</style>