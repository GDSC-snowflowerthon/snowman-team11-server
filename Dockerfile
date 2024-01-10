FROM openjdk:17-alpine as builder

COPY ./build/libs/snowman-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app

RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17-alpine
WORKDIR /app

COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.JarLauncher"]