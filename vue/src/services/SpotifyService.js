import axios from 'axios';
const http = axios.create({
  baseURL: 'https://api.spotify.com/v1',
});
const token = 'BQA-2wBi7vwXuzAXS7GYelz_gVR8f-jN_Z3Ji9_9hXyeBtJFrTjwsZingCQDQAZOgNTsTgH9RoCt1oAowkUmnsMIrXJGuaDjnrQJGb_ucj-DDkBLURw'; // Replace with your actual token
http.defaults.headers.common['Authorization'] = `Bearer ${token}`;
export default {
  getTrack(trackId) {
    return http.get(`/tracks/${trackId}`);
  },
  searchTrack(query) {
    return http.get(`/search`, {
      params: {
        q: query,
        type: 'track',
        limit: 10, // Limit results to 10 tracks
      },
    });
  },
};