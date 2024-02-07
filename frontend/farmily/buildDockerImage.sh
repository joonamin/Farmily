#!/bin/bash

# DockerFile을 기반으로 Docker Image를 만듭니다.
docker build --build-arg VITE_API_BASE_URL={ .env 파일 참고 } --build-arg VITE_OPENVIDU_URL={ .env 파일 참고 } -t joonamin44/ssafy_farmily:farmily_fe .