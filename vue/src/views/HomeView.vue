<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <EventForm />
  </div>
</template>

<script>
import EventForm from '../components/EventForm.vue';
export default {
  components:{
    EventForm
  }
};
</script>
