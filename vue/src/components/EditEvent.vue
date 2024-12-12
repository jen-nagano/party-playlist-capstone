<template>
    <div>
      <h3>Edit Event</h3>
      <form @submit.prevent="saveChanges" class="event-form">
        <div class="form-group">
        <label for="event-name">Event Name:</label>
        <input id="event-name" type="text" class="form-control" autocomplete="off" v-model="localEvent.name" />
        </div>
        <div class="form-group">
        <label for="event-date">Date:</label>
        <input id="event-date" type="date" class="form-control date-input" v-model="localEvent.date" />
        </div>
        <div class="form-group">
        <label for="start-time">Start Time:</label>
        <input id="start-time" type="time" class="form-control time-input" v-model="localEvent.start_time" />
        </div>
        <div class="form-group">
        <label for="end-time">End Time:</label>
        <input id="end-time" type="time" class="form-control time-input" v-model="localEvent.end_time" />
        </div>
        <div class="form-buttons">
        <button class="btn btn-submit" type="submit">Save Changes</button>
        <button class="btn btn-cancel" @click.prevent="cancelEdit" type="button">Cancel</button>
        </div>
    </form>
  
        <!-- <button @click.prevent="cancelEdit">Cancel</button> -->
      
    </div>
  </template>
  
  <script>
  export default {
    props: {
      event: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        // Create a local copy of the event to edit
        localEvent: { ...this.event },
      };
    },
    methods: {
      saveChanges() {
        // Emit the updated event to the parent
        this.$emit('save-event', this.localEvent);
        this.$emit('close-edit'); // Notify the parent to close the form
      },
      cancelEdit() {
        // Emit the original event back to the parent to reset
        this.$emit('cancel-edit', this.event);  // Will reset form fields
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