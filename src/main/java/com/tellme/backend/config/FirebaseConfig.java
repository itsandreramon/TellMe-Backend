/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  @PostConstruct
  public void init() throws IOException {
    FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(getServiceAccountKeyStream()))
            .setDatabaseUrl("https://tellme-a0cbc.firebaseio.com")
            .build();

    FirebaseApp.initializeApp(options);
  }

  private InputStream getServiceAccountKeyStream() {
    try {
      final var key = getClass().getResourceAsStream("/serviceAccountKey.json");

      if (key != null) {
        return key;
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Please make sure to place the Firebase serviceAccountKey.json inside the resources folder.");
    }

    throw new IllegalArgumentException(
            "Please make sure to place the Firebase serviceAccountKey.json inside the resources folder.");
  }
}
