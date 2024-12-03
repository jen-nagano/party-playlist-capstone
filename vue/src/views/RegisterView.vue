<template>
  <div id="register" class="text-center">
    <form v-on:submit.prevent="register" class="fade-up">
      <h1 class="form-title">Create Account</h1>
      <!-- Error Messages -->
      <div role="alert" v-if="registrationErrors" class="error-message fade-up">
        {{ registrationErrorMsg }}
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
      <div class="form-input-group fade-up">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <!-- Submit Button -->
      <button type="submit" class="submit-button fade-up">Create Account</button>
      <!-- Link to Login -->
      <p><router-link v-bind:to="{ name: 'login' }" class="login-link fade-up">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</template>
<script>
import authService from '../services/AuthService';
export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else if (this.user.password.length < 8 ||  !/[^a-zA-Z0-9]/.test(this.user.password) || !/[a-z]/.test(this.user.password)  || !/[A-Z]/.test(this.user.password)  || !/[0-9]/.test(this.user.password)){
          this.registrationErrors = true;
          this.registrationErrorMsg = 'Invalid password. Password must be at least 8 characters long and contain a lowercase letter, an uppercase letters, and a special character.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>
<style scoped>
/* Global Dark Theme */
#register {
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
/* Login Link */
.login-link {
  color: #ECF0F1;
  text-decoration: none;
  font-size: 1.1rem;
  transition: color 0.3s ease;
}
.login-link:hover {
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