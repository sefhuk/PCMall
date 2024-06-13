package com.team5.project2.product.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        FileInputStream stream = new FileInputStream("src/main/java/com/team5/project2/product/config/firebaseKey.json");
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(stream))
            .setStorageBucket("elice-project2.appspot.com")
            .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public Bucket bucket() throws IOException {
        return StorageClient.getInstance(firebaseApp()).bucket();
    }

    @Bean
    public Storage storage() throws IOException {
        StorageOptions storageOptions = StorageOptions.newBuilder()
            .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("src/main/java/com/team5/project2/product/config/firebaseKey.json")))
            .build();
        return storageOptions.getService();
    }
}