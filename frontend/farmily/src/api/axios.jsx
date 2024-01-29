import axios from "axios";

// 토큰 값 설정
const token =
  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJHT09HTEVfMTA2MTA2ODE1MiIsImV4cCI6MTcwNjU0OTA4NiwiaWF0IjoxNzA2NTMxMDg2LCJpc3MiOiJmYXJtaWx5Iiwicm9sZXMiOiJVU0VSIn0.aQ27tRymVpDTjx-lBu5fO0Sen1SGGM5I_TvARlmVXWbTpv-yq_AbsPemb47jJ2Oj5QxHjoKxqOLZIvl1qZK9Ow";
const accessToken = `Bearer ${token}`;

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    Authorization: accessToken,
  },
});

export default instance;
