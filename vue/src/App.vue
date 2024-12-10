<template>
  <div id="capstone-app">
    <!-- Conditionally render the navbar -->
    <div id="nav" v-if="showNavbar">
      <div class="nav-container">
        <!-- Logo Section -->
        <div class="logo-container">
          <router-link v-bind:to="{ name: 'home' }">
            <img src="/src/img/nav.png" alt="Logo" class="logo-img" />
          </router-link>
          <!-- User Greeting (Welcome text) on the left, next to logo -->
          <li v-if="isAuthenticated">
            <span class="user-indicator">Welcome, {{ username }}</span>
          </li>
        </div>
        <!-- Title Section (Centered between the left side and right side) -->
        <h1 class="nav-title">Panda Party Playlist</h1>
        <!-- Navigation Links (Right side with Logout Link) -->
        <div class="nav-links">
          <!-- Logout Link -->
          <router-link v-if="isAuthenticated" v-bind:to="{ name: 'logout' }" class="nav-link">Logout</router-link>
        </div>
      </div>
    </div>
    <!-- Main Content -->
    <router-view />
  </div>
</template>
<script>
export default {
  computed: {
    // Logic to determine whether to show the navbar
    showNavbar() {
      return !this.$route.meta.hideNavbar;
    },
    // Check if the user is authenticated
    isAuthenticated() {
      return this.$store.state.token !== ''; // Check if token exists
    },
    // Get the username from the store or fallback to 'Guest'
    username() {
      return this.$store.state.user?.username || 'Guest';
    }
  }
};
</script>
<style scoped>
/* Global Dark Theme */
#capstone-app {
  font-family: 'Arial', sans-serif;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background: linear-gradient(to right, #0B0B0B, #181616); /* Vibrant pink gradient */
  color: #ECF0F1;
}
#nav {
  background-color: #741577;
  padding: 20px 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
  border-radius: 15px; /* Rounded corners for a more stylish look */
  transition: background-color 0.3s ease-in-out, transform 0.3s ease-in-out;
  position: sticky;
  top: 0;
  z-index: 1000;
  /* Neon border effect */
  border: 2px solid transparent;
  box-shadow: 0 0 15px 5px rgba(255, 105, 180, 0.9), 0 0 20px 10px rgba(255, 105, 180, 0.7); /* Glowing neon effect */
}
li {
  list-style-type: none; /* Removes the default bullet */
}
.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}
.logo-container {
  display: flex;
  align-items: center;
  justify-content: flex-start; /* Ensure the user greeting is next to the logo */
}
.logo-img {
  width: 101px; /* Increased size by 45% */
  height: auto;
  margin-right: 15px; /* Space between image and text */
  transition: transform 0.3s ease;
}
.nav-title {
  font-size: 2.5rem;
  font-weight: bold;
  color: #fff;
  text-align: center;
  flex-grow: 1; /* Ensures title takes up remaining space */
  position: absolute;
  left: 50%;
  transform: translateX(-50%); /* Center the title */
}
.nav-links {
  display: flex;
  align-items: center;
}
.nav-link {
  margin-left: 30px;
  color: #fff;
  text-decoration: none;
  font-size: 1.2rem;
  text-transform: uppercase;
  position: relative;
  padding: 5px 10px;
  transition: color 0.3s ease, transform 0.3s ease, text-shadow 0.3s ease;
}
.nav-link:before {
  content: "";
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: #FF6F61;
  bottom: 0;
  left: 0;
  transform: scaleX(0);
  transform-origin: bottom right;
  transition: transform 0.25s ease-in-out;
}
.nav-link:hover {
  color: #DB34A9; /* Vibrant neon blue */
  transform: scale(1.1) rotate(3deg);
  text-shadow: 0 0 15px #3498DB, 0 0 30px #3498DB; /* Glowing neon blue */
}
.nav-link:hover:before {
  transform: scaleX(1);
  transform-origin: bottom left;
}
#nav:hover {
  background-color: #0D0D0E;
  transform: translateY(-5px); /* 3D floating effect */
}
/* Add styles for user-indicator */
.user-indicator {
  color: #fff;
  font-size: 1.1rem;
  margin-left: 20px;
}
button {
  background-color: #8E44AD;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 5px;
}
button:hover {
  background-color: #6A2C9C;
}
/* Responsive Design */
@media screen and (max-width: 768px) {
  .nav-container {
    flex-direction: column;
    align-items: center;
  }
  .nav-links {
    flex-direction: column;
    margin-top: 10px;
  }
  .nav-link {
    margin: 15px 0;
  }
}
router-view {
  flex-grow: 1;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
  transition: all 0.3s ease-in-out;
  opacity: 0;
  transform: translateY(20px);
  animation: fadeUp 1s ease forwards;
}
@keyframes fadeUp {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>













