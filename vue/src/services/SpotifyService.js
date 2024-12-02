import axios from 'axios';

const http = axios.create({
  baseURL: 'https:api.spotify.com/v1'
});

const token = 'BQCiL30pB_4lveBb_hYkM-JCjaWNLjyEPNGi-0nxASb0V8mSTGsEqyKWhcEln5zwSmNdjYeTkY5nsTS67fMZ1xC9BII6szIju9RAIHGmmGIz5CPqpZo'; // Replace with your actual token
http.defaults.headers.common['Authorization'] = `Bearer ${token}`;

export default {

  getTrack(trackId){
    return http.get(`tracks/${trackId}`)
  },

//   async playSong(trackId, code) {
//     const clientId = '648b164269cd4b96a741bae8f6cedb86';
//     const redirectUri = 'http://localhost:5173';
//     const scope = 'user-read-playback-state user-modify-playback-state streaming';
//     const authUrl = `https://accounts.spotify.com/authorize?client_id=${clientId}&response_type=code&redirect_uri=${redirectUri}&scope=${scope}`;
//     const clientSecret = 'e83abb1ddaf143dd9b402e5773205dfd';
//     const tokenUrl = 'https://accounts.spotify.com/api/token';
//     const body = new URLSearchParams({
//       code: code,
//       redirect_uri: redirectUri,
//       grant_type: 'authorization_code',
//     });
  
//     const response = await fetch(tokenUrl, {
//       method: 'POST',
//       headers: {
//         'Authorization': 'Basic ' + btoa(`${clientId}:${clientSecret}`),
//         'Content-Type': 'application/x-www-form-urlencoded',
//       },
//       body: body,
//     });
  
//     const data = await response.json();
//     return data.access_token; // Store this token to use for making API requests

//   }

}

