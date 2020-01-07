[![Build Status](https://img.shields.io/circleci/build/github/itsandreramon/TellMe-Backend?token=925c8971ba4c49e3bde6365d720689b70d48a965)](https://circleci.com/gh/itsandreramon/TellMe-Backend)

# TellMe-Backend
The REST backend for TellMe. Written entirely in Java using Spring Boot and Firebase.

### Development
You can start the Spring Boot application in a variety of ways:

1. Build a .jar from the sources and run it via CLI
```
$ ./gradlew bootJar
$ java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

2. Run it locally via the Heroku CLI
```
$ ./gradlew stage
$ heroku local web
```

### Deploy via Heroku
```
$ ./gradlew bootJar
$ heroku deploy:jar build/libs/backend-0.0.1-SNAPSHOT.jar --app tellme-backend
```

### Accessing the Heroku Deployment
The Heroku deployment can be accessed via the following URL:
```
https://tellme-backend.herokuapp.com/api/v1/tellme/
```

### API
You can obtain a valid api key by decrypting the file using:
```
$ openssl aes-256-cbc -d -in release/serviceAccountKey.json.encrypted -k $KEY >> src/main/resources/serviceAccountKey.json
```

*Please make sure to place the corresponding ```serviceAccountKey.json``` inside the ```src/main/resources``` folder.*
