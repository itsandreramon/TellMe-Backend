package com.tellme.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("./serviceAccountKey.json")
    private String serviceAccountKeyPath;

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyPath);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://tellme-a0cbc.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
