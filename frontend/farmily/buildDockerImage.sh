#!/bin/bash

# DockerFile을 기반으로 Docker Image를 만듭니다.
docker build --build-arg VITE_API_URL=https://i10e102.p.ssafy.io/api --build-arg VITE_OPENVIDU_URL=https://i10e102.p.ssafy.io:4443 -t joonamin44/ssafy_farmily:farmily_fe .
docker push joonamin44/ssafy_farmily:farmily_fe