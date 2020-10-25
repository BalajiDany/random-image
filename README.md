Requirements
1. Maven CLI
2. Java 8

[Steps]
1. Extract random-image
2. open CMD Inside the random-image
3. run => mvn clean install
4. run => mvn spring-boot:run

Note *
1. It will open in port 8080. make sure it should be free.
2. No need to for data base configuration.


[URL]
http://localhost:8080/1
http://localhost:8080/images

[database console]
http://localhost:8080/h2-console/

Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: <empty>

Table Name: IMAGE_REFERENCE
