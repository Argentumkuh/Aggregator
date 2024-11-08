# Starting the app

### Base documentation

1. Copy code from Github
2. To build project you need to have installed jdk 17 and maven
3. In case of Windows PATH directories must be set for java and maven (like ${maven.home}\bin)
4. Run _mvn clean install_ command to get application package
5. Go to project directory, _/target_ folder
6. Run java -jar aggregator-0.0.1-SNAPSHOT.jar from terminal
7. You must have appropriately configured to yml mappings databases (unfortunately I had not enough time to provide test Docker containers with databases)
8. Only Postgres database strategy is supported for now
9. You can find OpenAPI documentation for application endpoint on http://localhost:8080/v3/api-docs
10. Also Swagger UI is presented on http://localhost:8080/swagger-ui/index.html
11. Dockerfile and docker-compose not work for now