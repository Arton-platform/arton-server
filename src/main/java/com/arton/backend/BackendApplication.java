package com.arton.backend;


import com.arton.backend.artist.adapter.out.persistence.repository.ArtistRepository;
import com.arton.backend.follow.adapter.out.persistence.repository.FollowRepository;
import com.arton.backend.image.adapter.out.persistence.repository.UserImageRepository;
import com.arton.backend.performance.adapter.out.persistence.repository.PerformanceRepository;
import com.arton.backend.review.adapter.out.persistence.ReviewRepository;
import com.arton.backend.search.adapter.out.persistence.repository.UserSearchRepository;
import com.arton.backend.test.TestDataInit;
import com.arton.backend.user.adapter.out.persistence.repository.UserRepository;
import com.arton.backend.zzim.application.port.out.ZzimRepositoryPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	@Profile("dev")
	@Bean
	public TestDataInit testDataInit(ArtistRepository artistRepository, PerformanceRepository performanceRepository, UserRepository userRepository, FollowRepository followRepository,
									 ReviewRepository reviewRepository, UserImageRepository userImageRepository, PasswordEncoder passwordEncoder, UserSearchRepository userSearchRepository, ZzimRepositoryPort zzimRepositoryPort) {
		return new TestDataInit(artistRepository, performanceRepository, userRepository, followRepository, reviewRepository, userImageRepository, passwordEncoder, userSearchRepository, zzimRepositoryPort);
	}

}
