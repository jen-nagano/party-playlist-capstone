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
      <button class="btn btn-submit" type="submit">Submit</button>
      <button class="btn btn-cancel" v-on:click="cancelForm" type="button">Cancel</button>
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
.event-form {
  background: #2C2C54;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
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
  color: #FFFFFF;
  margin-bottom: 0.5rem;
}
.form-control {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 2px solid #F39C12;
  border-radius: 5px;
  background: #1E272E;
  color: #FFFFFF;
  outline: none;
  box-sizing: border-box;
}
.form-control:focus {
  border-color: #F1C40F;
  box-shadow: 0 0 5px #F1C40F;
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
}
.btn-submit {
  background: #28A745;
  color: #FFFFFF;
}
.btn-submit:hover {
  background: #218838;
}
.btn-cancel {
  background: #DC3545;
  color: #FFFFFF;
}
.btn-cancel:hover {
  background: #C82333;
}
</style>