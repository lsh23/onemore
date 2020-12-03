FROM        openjdk:11-jre
COPY        build/libs/onemore-*.jar app.jar
ENTRYPOINT  ["java","-jar","app.jar","--spring.config.location=classpath:/real-application.yml"]