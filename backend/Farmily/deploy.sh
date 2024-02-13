#/bin/bash

# bootJar
./gradlew bootJar
# docker Image build
docker build -t joonamin44/ssafy_farmily:farmily_be .
# docker hub push
docker push joonamin44/ssafy_farmily:farmily_be