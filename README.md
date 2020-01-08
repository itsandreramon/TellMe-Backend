[![Build Status](https://img.shields.io/circleci/build/github/itsandreramon/TellMe-Backend?token=925c8971ba4c49e3bde6365d720689b70d48a965)](https://circleci.com/gh/itsandreramon/TellMe-Backend)
[![Build Status](https://img.shields.io/uptimerobot/ratio/m784110259-46982a9bb52d30acae5d40e6)](https://uptimerobot.com/dashboard#784110259)

# TellMe-Backend
The REST backend for TellMe. Written entirely in Java using [Spring Boot](https://github.com/spring-projects/spring-boot) and [Firebase](https://github.com/firebase/firebase-admin-java).

### Development
You can start the Spring Boot application in a variety of ways:

1. Build a .jar from the sources and run it locally via the Java CLI
```
$ ./gradlew build
$ java -jar build/libs/backend-0.1.jar
```

2. Run it locally via the Heroku CLI
```
$ ./gradlew build
$ heroku local web
```

### Deploy via Heroku
```
$ ./gradlew bootJar
$ heroku deploy:jar build/libs/backend-0.1.jar --app tellme-backend
```

### Accessing the Heroku Deployment
The Heroku deployment can be accessed via the following URL:
```
https://tellme-backend.herokuapp.com/api/v1/tellme/
```

### Code style
This project uses [google-java-format](https://github.com/google/google-java-format) via [Spotless](https://github.com/diffplug/spotless) to format code based on the official [Google Java Style](https://google.github.io/styleguide/javaguide.html).
### API
You can obtain a valid api key by decrypting the file using:
```
$ openssl aes-256-cbc -d -in release/serviceAccountKey.json.encrypted -k $KEY >> src/main/resources/serviceAccountKey.json
```

*Please make sure to place the corresponding ```serviceAccountKey.json``` inside the ```src/main/resources``` folder.*