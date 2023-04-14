FROM openjdk:17
EXPOSE 8082
ADD target/inventory-service.jar inventory-service.jar
ENTRYPOINT ["java","-jar","/inventory-service.jar"]