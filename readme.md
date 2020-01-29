[![Build Status](https://img.shields.io/circleci/build/github/itsandreramon/TellMe-Backend?token=925c8971ba4c49e3bde6365d720689b70d48a965)](https://circleci.com/gh/itsandreramon/TellMe-Backend)

The REST backend for TellMe. Written entirely in Java using [Spring Boot](https://github.com/spring-projects/spring-boot) and [Project Reactor](https://github.com/reactor/reactor-core).

- Fully non-blocking & asynchronous I/O
- Uses [Reactor Netty](https://github.com/reactor/reactor-netty) Web Server
- Uses [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) for persistence
- Uses [CircleCI](https://circleci.com/) for Continous Integration
- ```master``` gets automatically deployed to [Heroku](https://www.heroku.com/)

### Development
You can start the Spring Boot application in a variety of ways:

1. Build a .jar from the sources and run it locally via the Java CLI
```
$ ./mvnw package
$ java -jar target/backend-0.2.jar
```

2. Run it locally via the Heroku CLI
```
$ ./mvnw package
$ heroku local web
```

### Accessing the Heroku Deployment
The Heroku deployment can be accessed via the following URL:
```
$ curl https://tellme-backend.herokuapp.com/
```

### API
You can obtain a valid api key by decrypting the file using:
```
$ openssl aes-256-cbc -d -in release/serviceAccountKey.json.encrypted -k $KEY >> src/main/resources/serviceAccountKey.json
```

*Please make sure to place the corresponding ```serviceAccountKey.json``` inside the ```src/main/resources``` folder.*
