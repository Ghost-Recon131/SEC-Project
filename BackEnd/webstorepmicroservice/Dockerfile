# Add java 8 package
FROM openjdk:8-jdk-alpine
LABEL maintainer ="s3843790@student.rmit.edu.au"

# External port to use
EXPOSE 8080

# Set target jar file to use
ADD target/webstorepmicroservice-0.0.1-SNAPSHOT.jar webstorepmicroservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/webstorepmicroservice-0.0.1-SNAPSHOT.jar"]