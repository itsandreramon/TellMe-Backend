package com.tellme.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(getServiceAccountKeyStream()))
                .setDatabaseUrl("https://tellme-a0cbc.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    private static InputStream getServiceAccountKeyStream() {
        try {
            return new ClassPathResource("/serviceAccountKey.json").getInputStream();
        } catch (Exception e) {
            throw new IllegalArgumentException("Please make sure to place the Firebase serviceAccountKey.json inside the resources folder.");
        }
    }
}
