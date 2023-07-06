package com.arton.backend.infra.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class FCMConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/arton-6757b-firebase-adminsdk-7fl96-aa49bd228f.json");
        InputStream serviceAccount = classPathResource.getInputStream();
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> apps = FirebaseApp.getApps();

        if (!ObjectUtils.isEmpty(apps)) {
            firebaseApp = apps.stream().filter(app -> app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)).findFirst().orElse(null);
        } else {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
