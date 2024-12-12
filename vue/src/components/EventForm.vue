<template>
  <form v-on:submit.prevent="submitForm" class="event-form">
    <div class="form-group">
      <label for="event-name">Event Name:</label>
      <input id="event-name" type="text" class="form-control" v-model="editEvent.name" autocomplete="off" />
    </div>
    <div class="form-group">
      <label for="event-date">Date:</label>
      <input id="event-date" type="date" class="form-control date-input" v-model="editEvent.date" />
    </div>
    <div class="form-group">
      <label for="start-time">Start Time:</label>
      <input id="start-time" type="time" class="form-control time-input" v-model="editEvent.startTime" />
    </div>
    <div class="form-group">
      <label for="end-time">End Time:</label>
      <input id="end-time" type="time" class="form-control time-input" v-model="editEvent.endTime" />
    </div>
    <div class="form-buttons">
      <button class="btn btn-cancel" v-on:click="cancelForm" type="button">Cancel</button>
      <button class="btn btn-submit" type="submit">Submit</button>
    </div>
  </form>
</template>
<script>
import EventService from '../services/EventService';
export default {
  data() {
    return {
      editEvent: {
        name: '',
        date: '',
        startTime: '',
        endTime: '',
        creator: this.$store.state.user.id,
      },
    };
  },
  methods: {
    submitForm() {
      EventService.addEvent(this.editEvent)
        .then((response) => {
          if (response.status === 201) {
            const eventId = response.data.id;
            this.$router.push({ name: 'EventView', params: { eventId } });
          }
        })
        .catch((error) => {
          console.error('Error adding event:', error);
        });
      this.$emit('formSubmitted');
    },
    cancelForm() {
      this.$emit('cancel');
    },
  },
};
</script>
<style scoped>



/*sample styles */
.event-form {
  background: #333; /* Dark grey background */
  padding: 2rem;
  border-radius: 10px;
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  font-size: 1.2rem;
  font-weight: bold;
  color: #fff; /* White text */
  margin-bottom: 0.5rem;
}

.form-control {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 2px solid #aaa; /* Light grey border */
  border-radius: 5px;
  background: #222; /* Dark background for input */
  color: #fff; /* White text */
  outline: none;
  box-sizing: border-box;
}

.form-control:focus {
  border-color: #9B59B6; /* Purple focus border */
  box-shadow: 0 0 5px #9B59B6; /* Purple focus shadow */
}

.date-input::-webkit-calendar-picker-indicator,
.time-input::-webkit-calendar-picker-indicator {
  background-color: white;
  border-radius: 50%;
  padding: 5px;
}

.form-buttons {
  display: flex;
  justify-content: space-between;
}

.btn {
  font-size: 1rem;
  padding: 0.7rem 1.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
  background: #9B59B6; /* Purple background */
  color: #fff; /* White text */
}

.btn:hover {
  background: #8e44ad; /* Darker purple on hover */
}

.btn-submit {
  background: #9B59B6; /* Default purple for submit */
}

.btn-submit:hover {
  background: #8e44ad; /* Darker purple for submit on hover */
}

.btn-cancel {
  background: #af7ac5; /* Lighter purple for cancel */
}

.btn-cancel:hover {
  background: #9b59b6; /* Darker purple for cancel on hover */
}
</style>