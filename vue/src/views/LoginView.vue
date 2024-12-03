<template>
  <div id="login" class="login-container">
    <form v-on:submit.prevent="login" class="fade-up">
      <h1 class="form-title">Please Sign In</h1>
      <!-- Error Messages -->
      <div role="alert" v-if="invalidCredentials" class="error-message fade-up">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration" class="error-message fade-up">
        Thank you for registering, please sign in.
      </div>
      <!-- Form Input Groups -->
      <div class="form-input-group fade-up">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group fade-up">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <!-- Submit Button -->
      <button type="submit" class="submit-button fade-up">Sign in</button>
      <!-- Register Link -->
      <p><router-link v-bind:to="{ name: 'register' }" class="register-link fade-up">Need an account? Sign up.</router-link></p>
    </form>
  </div>
</template>
<script>
import authService from "../services/AuthService";
export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status === 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/"); // Redirect to home after login
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>
<style scoped>
/* Global Dark Theme */
#login {
  background-color: #1A1A1A; /* Dark background */
  color: #fff;
  font-family: 'Roboto', sans-serif;
  padding: 50px 20px;
  border-radius: 10px;
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}
/* Title Styling */
.form-title {
  font-size: 2rem;
  margin-bottom: 20px;
  color: #ECF0F1;
}
/* Form Input Groups */
.form-input-group {
  margin-bottom: 1.5rem;
  opacity: 0; /* Start as invisible */
  transform: translateY(20px); /* Start slightly below */
  animation: fadeUp 1s ease forwards; /* Apply fade-up animation */
}
label {
  font-size: 1.2rem;
  color: #ECF0F1;
  margin-bottom: 5px;
  display: block;
}
input {
  width: 100%;
  padding: 12px;
  background-color: #34495E; /* Dark input field */
  border: 2px solid #2C3E50;
  border-radius: 5px;
  color: #fff;
  font-size: 1rem;
  outline: none;
  transition: all 0.3s ease;
}
input:focus {
  border-color: #3498DB; /* Blue border on focus */
  box-shadow: 0 0 8px rgba(52, 152, 219, 0.8); /* Blue glow on focus */
}
/* Error Message */
.error-message {
  background-color: #E74C3C;
  color: #fff;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 20px;
  font-size: 1.1rem;
  opacity: 0; /* Start as invisible */
  transform: translateY(20px); /* Start slightly below */
  animation: fadeUp 1s ease forwards; /* Apply fade-up animation */
}
/* Submit Button */
.submit-button {
  width: 100%;
  padding: 15px;
  background: linear-gradient(45deg, #3498DB, #9B59B6);
  color: white;
  border: none;
  border-radius: 50px;
  font-size: 1.2rem;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
.submit-button:hover {
  transform: scale(1.05); /* Button enlarges slightly on hover */
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
}
.submit-button:active {
  transform: scale(1); /* Active state returns to original size */
}
/* Register Link */
.register-link {
  color: #ECF0F1;
  text-decoration: none;
  font-size: 1.1rem;
  transition: color 0.3s ease;
}
.register-link:hover {
  color: #3498DB;
}
/* Fade-Up Animation */
@keyframes fadeUp {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>