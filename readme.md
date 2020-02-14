[![Build Status](https://img.shields.io/circleci/build/github/itsandreramon/TellMe-Backend?token=925c8971ba4c49e3bde6365d720689b70d48a965)](https://circleci.com/gh/itsandreramon/TellMe-Backend)

# TellMe-Backend
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

### Docker
This project can be built as a Docker image.

1. Create an image using:
```
$ docker build . --tag "backend-tellme" \
	--build-arg db_username="$TELLME_MONGODB_ATLAS_USERNAME" \
	--build-arg db_password="$TELLME_MONGODB_ATLAS_PASSWORD"
```

2. Run the image using:
```
$ docker run -d -p 8080:8080 backend-tellme:latest
```

### API
You can obtain a valid api key by decrypting the file using:
```
$ openssl aes-256-cbc -d -in release/serviceAccountKey.json.encrypted -k $KEY >> src/main/resources/serviceAccountKey.json
```

*Please make sure to place the corresponding ```serviceAccountKey.json``` inside the ```src/main/resources``` folder.*
