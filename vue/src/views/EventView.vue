<template>
    <div class="Event">
      <h1>Event</h1>
      <p>Complete the form to add a new event.</p>
      <EventForm />
    </div>
  </template>
  
  <script>
  import EventForm from '../components/EventForm.vue';

  export default {
    name: 'EventView',
    components:{
      EventForm
    }
  };
  </script>