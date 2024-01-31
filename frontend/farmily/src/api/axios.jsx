import axios from 'axios';

// 토큰 값 설정
const token = '';
const accessToken = `Bearer ${token}`;

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  // headers: {
  //   Authorization: accessToken,
  // },
});

export default instance;
