# TellMe-Backend
The REST backend for TellMe. Written entirely in Java using Spring Boot and Firebase.

### Deployment
```
$ ./gradlew bootJar
$ heroku deploy:jar build/libs/backend-0.0.1-SNAPSHOT.jar --app tellme-backend
```
