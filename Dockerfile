FROM ibm-semeru-runtimes:open-17-jdk-jammy
ARG JAR_FILE=target/*.jar
COPY ./target/java-mysql-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]