#java 11 사용하므로 openjdk:11 image사용 (8 error)
FROM openjdk:11-jdk

#기본 작업 디렉토리 설정
WORKDIR /app/backend

#https://iagreebut.tistory.com/171?category=887117
#jar파일 미리 생성 후 복사 (./gradlew build)
ADD build/libs/*.jar /app/backend/app.jar

RUN apt-get update && apt-get install -y wget
ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

#컨테이너가 실행될 때 명령어 수행
CMD dockerize -wait tcp://mysql:3306 -timeout 15s -wait tcp://redis:6379 java -jar /app/backend/app.jar