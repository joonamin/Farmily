import axios from "axios";

// 토큰 값 설정
const token ='eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJHT09HTEVfMTA1NDg5NjM5OCIsImV4cCI6MTcwNjY3MjQxNSwiaWF0IjoxNzA2NjcxNTE1LCJpc3MiOiJmYXJtaWx5Iiwicm9sZXMiOiJVU0VSIn0.MVUfBGF2kceqlaIYAc6ECGQRCt2Xj8r3wYxTbr-oQRYWoSZidB5q5NwBgdFp_sdJmRdMtG_zegwDvuhkgFDvlA'
 
const accessToken = `Bearer ${token}`;

const instance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    Authorization: accessToken,
  },
});

export default instance;
