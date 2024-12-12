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
  border-color: rgb(255, 105, 180, 0.9); /* Purple focus border */
  box-shadow: 0 0 5px rgb(255, 105, 180, 0.9); /* Purple focus shadow */
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
  background: rgb(255, 105, 180, 0.9); /* Purple background */
  color: #fff; /* White text */
}

.btn:hover {
  background: #8e44ad; /* Darker purple on hover */
}

.btn-submit {
  background: rgb(255, 105, 180, 0.9); /* Default purple for submit */
}

.btn-submit:hover {
  background: #8e44ad; /* Darker purple for submit on hover */
}

.btn-cancel {
  background: #af7ac5; /* Lighter purple for cancel */
}

.btn-cancel:hover {
  background: rgb(255, 105, 180, 0.9); /* Darker purple for cancel on hover */
}
</style>