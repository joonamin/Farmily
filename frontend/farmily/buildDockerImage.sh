#!/bin/bash

# DockerFile을 기반으로 Docker Image를 만듭니다.
docker build --build-arg VITE_API_BASE_URL=https://i10e102.p.ssafy.io/api --build-arg VITE_OPENVIDU_URL=https://i10e102.p.ssafy.io/kms mily_fe .
docker push joonamin44/ssafy_farmily:farmily_fe