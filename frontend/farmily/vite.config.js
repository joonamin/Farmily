import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],

  resolve: {
    alias: {
      react: "preact/compat",
      "react-dom": "preact/compat",
      "@": "/src",
    },
  },

  // 여기에 server 섹션 추가
  server: {
    host: '0.0.0.0',
    proxy: {
      // 예시: "/api" 경로로 시작하는 모든 요청을 "http://i10e102.p.ssafy.io:8080"으로 프록시
      "/api": {
        target: "http://i10e102.p.ssafy.io:8080",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
