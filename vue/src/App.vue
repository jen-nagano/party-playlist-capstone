<template>
  <div id="capstone-app">
    <div id="nav">
      <div class="nav-container">
        <router-link v-bind:to="{ name: 'home' }" class="nav-link logo">Panda Party Playlist</router-link>
        <div class="nav-links">
          <router-link v-bind:to="{ name: 'home' }" class="nav-link">Home</router-link>
          <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''" class="nav-link">Logout</router-link>
        </div>
      </div>
    </div>
    <router-view />
  </div>
</template>
<script>
export default {
  name: "App",
};
</script>
<style scoped>
#capstone-app {
  font-family: 'Arial', sans-serif;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background: linear-gradient(to right, #2C3E50, #34495E); /* Dark background gradient */
  color: #ECF0F1;
}
#nav {
  background-color: #2C3E50;
  color: white;
  padding: 20px 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease-in-out;
}
.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  font-size: 2rem;
  font-weight: bold;
  text-decoration: none;
  color: #fff;
  transition: color 0.3s ease;
}
.nav-links {
  display: flex;
}
.nav-link {
  margin-left: 25px;
  color: white;
  text-decoration: none;
  font-size: 1.2rem;
  transition: color 0.3s ease, transform 0.3s ease;
}
.nav-link:hover {
  color: #3498DB; /* Vibrant blue color */
  transform: scale(1.1); /* Slightly enlarge the text on hover */
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
