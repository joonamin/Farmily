import axios from 'axios';
const token = '';
const accessToken = `Bearer ${token}`;
const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    Authorization: accessToken,
  },
});
export default instance;
