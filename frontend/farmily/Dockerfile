FROM node:20-alpine as build

# build arguments 정의
ARG VITE_API_BASE_URL
ARG VITE_OPENVIDU_URL

# Build Process에서 environment variable set
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
ENV VITE_OPENVIDU_URL=$VITE_OPENVIDU_URL

WORKDIR /app
COPY . .

RUN npm install
RUN npm run build

