# 포팅 메뉴얼

# 1. 개요

## 사용 툴

- 이슈 관리 : jira
- 형상 관리 : GitLab
- 커뮤니케이션 : Notion, Mattermost
- 디자인 : Figma
- infra : docker-compose

## 개발 환경

- IntelliJ 17.0.9
- springboot 3.2.1
- Server : AWS EC2 Ubuntu 20.04.6 LTS
- DB : MariaDB
- gradle : 8.5
- openvidu : 2.22.0
- react : 18.2.0
- redux : 5.0.1

## 외부 서비스

- Kakao, Google OAuth : application.yml 참고
- Openvidu

## GitIgnore

- Spring : application.yml, application-dev.yml, application-local.yml (\src\main\resource)

# 2. 빌드

1. 환경변수 형태
    - `ａｐｐｌｉｃａｔｉｏｎ－｛ｐｒｏｆｉｌｅ｝．ｙｍｌ`

      환경변수　파일　참고


    ### Maria DB
    
    ```jsx
      datasource:
        driver-class-name: org.mariadb.jdbc.Driver # 다른 DB 사용할 시 변경
        url:     # JDBC URL
        username:     # DB 이름
        password:     # DB 패스워드
    ```
    
    ### Security OAuth
    
    ```jsx
    security:
        oauth2:
          client:
            registration:
              google:
                client-id:   # Google 클라이언트 ID
                client-secret:     # Google 클라이언트 Secret
              kakao:
                client-id:   # Kakao 클라이언트 ID
                redirect-uri: http://localhost:8080/login/oauth2/code/kakao
                scope:
                  - profile_image
                  - profile_nickname
                  - openid
              facebook:
                client-id:   # Meta 클라이언트 ID
                client-secret:    # Meta 클라이언트 Secret
                scope:
                  - openid
                  - public_profile
                  - email
    ```
    
    ### JWT 시크릿 키
    
    ```jsx
    jwt:
      secret:    # JWT Secret key
      redirect-uri: http://localhost:8080/oauth2/redirect
    
    # openvidu
    openvidu:
      url:    # OpenVidu KMS URL
      secret:    # OpenVidu KMS Secret
    ```

2. DB

   ### DB 덤프 파일

    ```jsx
    -- farmily_test 데이터베이스 구조 내보내기
    CREATE DATABASE IF NOT EXISTS `farmily_test` /*!40100 DEFAULT CHARACTER SET latin1 */;
    USE `farmily_test`;
    
    -- 테이블 farmily_test.achievement_reward_history 구조 내보내기
    CREATE TABLE IF NOT EXISTS `achievement_reward_history` (
      `created_at` datetime(6) DEFAULT NULL,
      `family_id` bigint(20) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `achievement` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKps3vkn4jpb1o51l3rhn3il5fx` (`family_id`),
      CONSTRAINT `FKps3vkn4jpb1o51l3rhn3il5fx` FOREIGN KEY (`family_id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.calendar_schedule 구조 내보내기
    CREATE TABLE IF NOT EXISTS `calendar_schedule` (
      `end_date` date DEFAULT NULL,
      `start_date` date DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `family_id` bigint(20) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `color` char(6) DEFAULT NULL,
      `memo` varchar(16) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKic05kmn40mxl1dow4un94a787` (`family_id`),
      CONSTRAINT `FKic05kmn40mxl1dow4un94a787` FOREIGN KEY (`family_id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.challenge_progress 구조 내보내기
    CREATE TABLE IF NOT EXISTS `challenge_progress` (
      `date` date DEFAULT NULL,
      `challenge_id` bigint(20) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKdthvxbjf83rcjf65ldxq7txwp` (`challenge_id`),
      CONSTRAINT `FKdthvxbjf83rcjf65ldxq7txwp` FOREIGN KEY (`challenge_id`) REFERENCES `challenge_record` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.challenge_record 구조 내보내기
    CREATE TABLE IF NOT EXISTS `challenge_record` (
      `end_date` date DEFAULT NULL,
      `is_rewarded` tinyint(1) DEFAULT NULL,
      `start_date` date DEFAULT NULL,
      `id` bigint(20) NOT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `FK20pwbp3jhkqjdawn8wcew4d26` FOREIGN KEY (`id`) REFERENCES `record` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.comment 구조 내보내기
    CREATE TABLE IF NOT EXISTS `comment` (
      `author_id` bigint(20) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `record_id` bigint(20) DEFAULT NULL,
      `content` text,
      PRIMARY KEY (`id`),
      KEY `FK2bam3knj13ijq6eiskx55xtqh` (`author_id`),
      KEY `FKjxmdtf6tvwiopsrd14muh3kv9` (`record_id`),
      CONSTRAINT `FK2bam3knj13ijq6eiskx55xtqh` FOREIGN KEY (`author_id`) REFERENCES `member` (`id`),
      CONSTRAINT `FKjxmdtf6tvwiopsrd14muh3kv9` FOREIGN KEY (`record_id`) REFERENCES `record` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.community_post 구조 내보내기
    CREATE TABLE IF NOT EXISTS `community_post` (
      `author_id` bigint(20) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `tree_image_id` bigint(20) DEFAULT NULL,
      `content` text,
      `title` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK_842w1t2rud6u7ykoj8etbvile` (`tree_image_id`),
      KEY `FK5fl7jc5o06opoco833oms179p` (`author_id`),
      CONSTRAINT `FK3mk9041b10aopu1uob9i0m1fo` FOREIGN KEY (`tree_image_id`) REFERENCES `image` (`id`),
      CONSTRAINT `FK5fl7jc5o06opoco833oms179p` FOREIGN KEY (`author_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.family 구조 내보내기
    CREATE TABLE IF NOT EXISTS `family` (
      `point` int(11) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `image_id` bigint(20) DEFAULT NULL,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `invitation_code` varchar(255) DEFAULT NULL,
      `motto` varchar(255) DEFAULT NULL,
      `name` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK_29644wrp69tu2lplnbmowdoy6` (`image_id`),
      CONSTRAINT `FK2da5arpuwny25jy0vfr07wqqt` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.family_fruit_skins 구조 내보내기
    CREATE TABLE IF NOT EXISTS `family_fruit_skins` (
      `id` bigint(20) NOT NULL,
      `challenge` varchar(32) DEFAULT NULL,
      `daily` varchar(32) DEFAULT NULL,
      `event` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `FKcu0ki4x3c4lbqnkdehkeq67t8` FOREIGN KEY (`id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.family_item 구조 내보내기
    CREATE TABLE IF NOT EXISTS `family_item` (
      `created_at` datetime(6) DEFAULT NULL,
      `family_id` bigint(20) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `code` varchar(32) DEFAULT NULL,
      `type` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FK4vb589y8voud07haukc8n1bok` (`family_id`),
      CONSTRAINT `FK4vb589y8voud07haukc8n1bok` FOREIGN KEY (`family_id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.family_membership 구조 내보내기
    CREATE TABLE IF NOT EXISTS `family_membership` (
      `created_at` datetime(6) DEFAULT NULL,
      `family_id` bigint(20) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `member_id` bigint(20) DEFAULT NULL,
      `role` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FK2r3uqw8xskfk04na0n0xtj0j7` (`family_id`),
      KEY `FKnj9myjgsf6dwosk1428sll5dq` (`member_id`),
      CONSTRAINT `FK2r3uqw8xskfk04na0n0xtj0j7` FOREIGN KEY (`family_id`) REFERENCES `family` (`id`),
      CONSTRAINT `FKnj9myjgsf6dwosk1428sll5dq` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.family_statistics 구조 내보내기
    CREATE TABLE IF NOT EXISTS `family_statistics` (
      `calendar_plan_count` int(11) DEFAULT NULL,
      `challenge_complete_count` int(11) DEFAULT NULL,
      `daily_record_count` int(11) DEFAULT NULL,
      `event_record_count` int(11) DEFAULT NULL,
      `harvest_count` int(11) DEFAULT NULL,
      `is_first_conference` bit(1) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL,
      `last_edited_at` datetime(6) DEFAULT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `FKbg5jip1waqkf78h4d9s2va3l7` FOREIGN KEY (`id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.image 구조 내보내기
    CREATE TABLE IF NOT EXISTS `image` (
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `location` varchar(255) DEFAULT NULL,
      `original_file_name` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.image_card 구조 내보내기
    CREATE TABLE IF NOT EXISTS `image_card` (
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `image_id` bigint(20) DEFAULT NULL,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `record_id` bigint(20) DEFAULT NULL,
      `description` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKsrhluai58ctkqo2tusmwt1tak` (`image_id`),
      KEY `FKcx8e3qj4j8gbykst40ilsswqf` (`record_id`),
      CONSTRAINT `FKcx8e3qj4j8gbykst40ilsswqf` FOREIGN KEY (`record_id`) REFERENCES `record` (`id`),
      CONSTRAINT `FKsrhluai58ctkqo2tusmwt1tak` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.member 구조 내보내기
    CREATE TABLE IF NOT EXISTS `member` (
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `profile_pic_id` bigint(20) DEFAULT NULL,
      `nickname` varchar(16) DEFAULT NULL,
      `password` varchar(32) DEFAULT NULL,
      `username` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK_s9ow3kw6f1fyk08a6o9f6mkgo` (`profile_pic_id`),
      CONSTRAINT `FKpk399pb8epv7df0scprf990v1` FOREIGN KEY (`profile_pic_id`) REFERENCES `image` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.placement 구조 내보내기
    CREATE TABLE IF NOT EXISTS `placement` (
      `col` int(11) DEFAULT NULL,
      `row` int(11) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `record_id` bigint(20) DEFAULT NULL,
      `tree_id` bigint(20) DEFAULT NULL,
      `dtype` varchar(31) NOT NULL,
      `type` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      UNIQUE KEY `UK_gllxlvi189gbr0urfrbl5u1da` (`record_id`),
      KEY `FKi1yy3ewtpmxi64cqi9a6rtkrg` (`tree_id`),
      CONSTRAINT `FKi1yy3ewtpmxi64cqi9a6rtkrg` FOREIGN KEY (`tree_id`) REFERENCES `tree` (`id`),
      CONSTRAINT `FKkodyshdwwqdbqbq8h92qcvxja` FOREIGN KEY (`record_id`) REFERENCES `record` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.record 구조 내보내기
    CREATE TABLE IF NOT EXISTS `record` (
      `author_id` bigint(20) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `sprint_id` bigint(20) DEFAULT NULL,
      `dtype` char(1) NOT NULL,
      `content` text,
      `title` varchar(255) DEFAULT NULL,
      `type` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKp6nets88dwbl7vp96bgt8ern1` (`author_id`),
      KEY `FKdrr1kp8lblduxnx7upm1bjef0` (`sprint_id`),
      CONSTRAINT `FKdrr1kp8lblduxnx7upm1bjef0` FOREIGN KEY (`sprint_id`) REFERENCES `sprint` (`id`),
      CONSTRAINT `FKp6nets88dwbl7vp96bgt8ern1` FOREIGN KEY (`author_id`) REFERENCES `member` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.sprint 구조 내보내기
    CREATE TABLE IF NOT EXISTS `sprint` (
      `end_date` date DEFAULT NULL,
      `is_harvested` tinyint(1) DEFAULT NULL,
      `start_date` date DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `family_id` bigint(20) DEFAULT NULL,
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `last_edited_at` datetime(6) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FKbyn1cexee61ytx1syqqksyt6l` (`family_id`),
      CONSTRAINT `FKbyn1cexee61ytx1syqqksyt6l` FOREIGN KEY (`family_id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
    
    -- 테이블 farmily_test.tree 구조 내보내기
    CREATE TABLE IF NOT EXISTS `tree` (
      `created_at` datetime(6) DEFAULT NULL,
      `id` bigint(20) NOT NULL,
      `last_edited_at` datetime(6) DEFAULT NULL,
      `type` varchar(32) DEFAULT NULL,
      PRIMARY KEY (`id`),
      CONSTRAINT `FKki2ufibbgjgn9bemp4hvyktlc` FOREIGN KEY (`id`) REFERENCES `family` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    ```

3. 배포　스크립트
    - ｂａｃｋｅｎｄ

        ```bash
        #/bin/bash
        
        # bootJar
        ./gradlew bootJar
        # docker Image build
        docker build -t ＄｛ＢＥ＿ＩＭＡＧＥ｝:＄｛ｔａｇ｝ .
        # docker hub push
        docker push ＄｛ＢＥ＿ＩＭＡＧＥ｝:＄｛ｔａｇ｝
        ```

    - ｆｒｏｎｔｅｎｄ

        ```bash
        #!/bin/bash
        
        # DockerFile을 기반으로 Docker Image를 만듭니다.
        docker build --build-arg VITE_API_URL=＄｛ＢＥ＿ＢＡＳＥ＿ＵＲＬ｝--build-arg VITE_OPENVIDU_URL=＄｛ＯＰＥＮＶＩＤＵ＿ＢＡＳＥ＿ＵＲＬ｝-t ＄｛ＦＥ＿ＩＭＡＧＥ｝ .
        docker push ＄｛ＦＥ＿ＩＭＡＧＥ｝:＄｛ｔａｇ｝
        ```

    - ＣＬＯＵＤ
        - `ｄｏｃｋｅｒ－ｃｏｍｐｏｓｅ．ｙｍｌ`

            ```bash
            version: "3"
            # service discovery 를 활용하여, 컨테이너 간 통신이 가능하게 하기 위함
            networks:
              front-network:
                driver: bridge
              back-network:
                driver: bridge
                
            services:
            
              farmily-be:
                # docker image 빌드시에 아래 이름으로 빌드합니다.
                image: ＃
                container_name: backend
                ports:
                  - "48080:5000"
                
                depends_on:
                  - mariadb
                  - redis
                environment:
                  - spring.profiles.active=dev
                  - SPRING_REDIS_HOST=farmily-redis-1
                  - SPRING_REDIS_PORT=6379
                volumes:
                  - /home/ubuntu/farmily-test/files:/home/ubuntu/farmily-test/files # localFileService가 write하는 위치입니다.
                networks:
                  - back-network
                  
                  
              mariadb:
                image: mariadb:latest
                ports:
                  - "43306:3306"  # 호스트에서의 직접 접근을 막기 위해 안쓰는 포트를 지정
                container_name: farmily-mariadb-1
                hostname: farmily-mariadb-1
                
                environment:
                  - "MYSQL_ROOT_PASSWORD="
                  - "MYSQL_DATABASE="
                  - "MYSQL_USER="
                  - "MYSQL_PASSWORD="
                  - "TZ=Asia/Seoul"
                command:
                  - --character-set-server=utf8mb4
                  - --collation-server=utf8mb4_unicode_ci
                volumes:
                  - ./mariadb_data:/var/lib/mysql
                networks:
                  - back-network  
            
              redis:
                image: redis:latest
                volumes:
                  - ./redis.conf:/data/redis.conf
                command: ["redis-server", "/data/redis.conf"]
                ports:
                  - "46379:6379"
                container_name: farmily-redis-1
                hostname: farmily-redis-1
                networks:
                  - back-network
            
              farmily-fe:
                image: ＃
                container_name: frontend
                ports:
                  - "5173:5173"
                command: npm run dev
                environment:
                  - HOST=0.0.0.0
                networks:
                  - front-network
            
              jenkins:
                image: jenkins/jenkins:jdk17
                container_name: jenkins
                user: root
                privileged: true
                volumes:
                  - /var/run/docker.sock:/var/run/docker.sock
                  - /jenkins:/var/jenkins_home
                ports:
                  - "8080:8080"
                  - "50000:50000"
                environment:
                  - "JENKINS_OPTS=--httpPort=8080"
                  - "JENKINS_SLAVE_AGENT_PORT=50000"
                networks:
                  - back-network
                  - front-network
                  - default
            
              nginx:
                image: nginx:1.18
                container_name: nginx
                restart: always
                ports:
                  - "80:80"
                  - "443:443"
                volumes:
                  - /etc/letsencrypt/live/i10e102.p.ssafy.io/fullchain.pem:/etc/letsencrypt/live/i10e102.p.ssafy.io/fullchain.pem
                  - /etc/letsencrypt/live/i10e102.p.ssafy.io/privkey.pem:/etc/letsencrypt/live/i10e102.p.ssafy.io/privkey.pem
                  - ./nginx.conf:/etc/nginx/nginx.conf
                  - /home/ubuntu/farmily-test/files:/home/ubuntu/farmily-test/files         # 위 경로는image 파일이 저장되는 경로입니다.
                    # - ./default_nginx.conf:/etc/nginx/nginx.conf
                networks:
                  - back-network
                  - front-network
                  - default
                environment:
                  - TZ=Asia/Seoul
                depends_on:
                  - farmily-be
                  - farmily-fe
            ```


        ```bash
        #!/bin/bash
        docker-compose down
        sleep 3s
        docker-compose pull
        sleep 5s
        docker-compose up -d
        ```