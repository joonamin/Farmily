import axios from "axios";

// 토큰 값 설정
const token ='eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJHT09HTEVfMTA2MTA2ODE1MiIsImV4cCI6MTcwNjU3NzU5NSwiaWF0IjoxNzA2NTc2Njk1LCJpc3MiOiJmYXJtaWx5Iiwicm9sZXMiOiJVU0VSIn0.9qTAwDfkoy7W11eWxuk7m2q6e95pmmRBb-vCJ2pCrceM2inTcxyQCYnELiRhLB7r7gZZvKR5Xt-lKDLVbGPj8A'
 
const accessToken = `Bearer ${token}`;

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    Authorization: accessToken,
  },
});

export default instance;
