[![Build Status](https://img.shields.io/circleci/build/github/itsandreramon/TellMe-Backend?token=925c8971ba4c49e3bde6365d720689b70d48a965)](https://circleci.com/gh/itsandreramon/TellMe-Backend)

# TellMe-Backend
The REST backend for TellMe. Written entirely in Java using Spring Boot and Firebase.

### Deployment via Heroku
```
$ ./gradlew bootJar
$ heroku deploy:jar build/libs/backend-0.0.1-SNAPSHOT.jar --app tellme-backend
```
