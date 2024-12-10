<template>
  <div class="landing-page">
    <!-- Banner Section with Video -->
    <div class="banner">
      <video class="banner-video" autoplay loop muted>
        <source src="/src/video/4092801-hd_1920_1080_30fps.mp4 - Copy.crdownload" type="video/mp4">
        <!-- Add fallback content for browsers that don't support the video tag -->
        Your browser does not support the video tag.
      </video>
      <h1 class="banner-title">Welcome to Panda Party Playlist!</h1>
    </div>
    <h1 class="form-title">Welcome to Panda Party Playlist!</h1>
    <!-- Button Container -->
    <div class="landing-page-buttons">
      <button @click="goToSignUp" class="submit-button">Sign Up</button>
      <button @click="goToLogin" class="submit-button">Login</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goToSignUp() {
      this.$router.push({ name: 'register' });
    },
    goToLogin() {
      this.$router.push({ name: 'login' });
    }
  }
};
</script>

<style scoped>
/* Center the content and apply background */
.landing-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(to right, #070707, #FCFAFB); /* Your vibrant background */
  color: #ECF0F1;
  font-family: 'Roboto', sans-serif;
  text-align: center;
}

/* Banner Section with Video */
.banner {
  width: 100%;
  height: 350px; /* Set a height for the banner section */
  position: relative;
  overflow: hidden;
}

.banner-video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.banner-title {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 3rem;
  font-weight: bold;
  text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5); /* Optional text shadow */
  color: #f1eff1; /* Purple color */
  animation: pulseGlow 1.5s ease-in-out infinite;
}

/* Title Animation */
.form-title {
  font-size: 2.5rem;
  color: #fff;
  margin-bottom: 50px;
  animation: bounce 2s infinite;
}

/* Button Container for vertical stack */
.landing-page-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px; /* Space between buttons */
  width: 100%;
  max-width: 600px; /* Max width for buttons */
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
