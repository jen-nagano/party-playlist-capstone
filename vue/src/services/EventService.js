import axios from 'axios';

const http = axios.create({
  baseURL: import.meta.env.VITE_REMOTE_API
});

export default {

  addEvent(event) {
    return http.post('/events', event)
  },

  updateEvent(event) {
    return http.put('/events', event)
  },

  addPlaylistToEvent(playlist, eventId ) {
    return http.post(`events/${eventId}/playlists`, playlist);
  }

}