[![Build Status](https://img.shields.io/circleci/build/github/itsandreramon/TellMe-Backend?token=925c8971ba4c49e3bde6365d720689b70d48a965)](https://circleci.com/gh/itsandreramon/TellMe-Backend)

The REST backend for TellMe. Written entirely in Java using [Spring Boot](https://github.com/spring-projects/spring-boot) and [Project Reactor](https://github.com/reactor/reactor-core).

- Fully non-blocking & asynchronous I/O
- Uses [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) for persistence
- Uses [Okta](https://www.okta.com/) for authorization
- Uses [CircleCI](https://circleci.com/) for Continous Integration
- ```master``` gets automatically deployed to [Heroku](https://www.heroku.com/)

### Development
You can start the Spring Boot application in a variety of ways:

1. Build a .jar from the sources and run it locally via the Java CLI
```
$ ./mvnw package
$ java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

2. Run it locally via the Heroku CLI
```
$ ./mvnw package
$ heroku local web
```
*Please make sure to built the project using Java 8 in order to acces the JAXB APIs. The JAXB APIs are considered Java EE APIs and were removed in Java 11.*

### Accessing the Heroku Deployment
The Heroku deployment can be accessed via the following URL:
```
$ curl https://tellme-backend.herokuapp.com/ -H "Authorization: Bearer $TOKEN"
```

### API
You can obtain a valid api key by decrypting the file using:
```
$ openssl aes-256-cbc -d -in release/serviceAccountKey.json.encrypted -k $KEY >> src/main/resources/serviceAccountKey.json
```

*Please make sure to place the corresponding ```serviceAccountKey.json``` inside the ```src/main/resources``` folder.*

### Disclaimer
This project was built for educational purposes and does not represent a production ready application.
