FROM openjdk:22-ea-1-jdk-oracle
WORKDIR /app
COPY target/PropertyManagment-0.0.1-SNAPSHOT.jar PropertyManagment.jar

CMD ["java","-jar","PropertyManagment.jar"]


