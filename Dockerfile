FROM openjdk:jdk-11.jdk
COPY *.sql /docker-entrypoint-initdb.d/
ADD build/libs/payment-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

