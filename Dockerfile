FROM openjdk:11-alpine
EXPOSE 8333
RUN mkdir /app
VOLUME /app
COPY ./build/libs/*.jar /app/app.jar
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8080
ENV USE_PROFILE aws
ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "/app/app.jar"]