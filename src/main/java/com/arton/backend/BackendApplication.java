package com.arton.backend;

import com.arton.backend.artist.adapter.out.repository.ArtistRepository;
import com.arton.backend.performance.adapter.out.repository.PerformanceRepository;
import com.arton.backend.test.TestDataInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public TestDataInit testDataInit(ArtistRepository artistRepository, PerformanceRepository performanceRepository) {
		return new TestDataInit(artistRepository, performanceRepository);
	}

}
