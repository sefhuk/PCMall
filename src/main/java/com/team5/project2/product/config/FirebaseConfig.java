package com.team5.project2.product.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        InputStream stream = new ByteArrayInputStream(System.getenv("FIREBASE_STORAGE_KEY").getBytes());
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(stream))
            .setStorageBucket(System.getenv("FIREBASE_STORAGE_URL"))
            .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public Bucket bucket() throws IOException {
        return StorageClient.getInstance(firebaseApp()).bucket();
    }
}
