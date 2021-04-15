FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAVA_OPTS
ARG JAR_FILE=target/*.jar
ENV JAVA_OPTS=$JAVA_OPTS
COPY ${JAR_FILE} backend.jar
EXPOSE 8080
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar backend.jar

# FROM openjdk:11-jdk-slim
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]
