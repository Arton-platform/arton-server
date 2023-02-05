package com.arton.backend;

import com.arton.backend.artist.adapter.out.repository.ArtistRepository;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepository;
import com.arton.backend.test.TestDataInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class BackendApplication {

	/**
	 * S3 지연 없애기
	 */
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Profile("local")
	@Bean
	public TestDataInit testDataInit(ArtistRepository artistRepository, PerformanceRepository performanceRepository, UserRepository userRepository, FollowRepository followRepository,
									 ReviewRepository reviewRepository, UserImageRepository userImageRepository, PasswordEncoder passwordEncoder) {
		return new TestDataInit(artistRepository, performanceRepository, userRepository, followRepository, reviewRepository, userImageRepository, passwordEncoder);
	}

}
