<template>
    <div>
      <h3>Edit Event</h3>
      <form @submit.prevent="saveChanges">
        <label>Name:</label>
        <input v-model="localEvent.name" type="text" />
  
        <label>Date:</label>
        <input v-model="localEvent.date" type="date" />
  
        <label>Start Time:</label>
        <input v-model="localEvent.start_time" type="time" />
  
        <label>End Time:</label>
        <input v-model="localEvent.end_time" type="time" />
  
        <button type="submit">Save</button>
        <button @click.prevent="cancelEdit">Cancel</button>
      </form>
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
      },
      cancelEdit() {
        // Emit the original event back to the parent to reset
        this.$emit('cancel-edit', this.event);  // Will reset form fields
      },
    },
  };
  </script>