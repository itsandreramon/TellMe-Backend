/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

	@PostConstruct
	public void init() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(getServiceAccountKeyStream())).build();

		FirebaseApp.initializeApp(options);
	}

	private static InputStream getServiceAccountKeyStream() {
		try {
			return new ClassPathResource("/serviceAccountKey.json").getInputStream();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Please make sure to place the Firebase serviceAccountKey.json inside the resources folder.");
		}
	}
}
