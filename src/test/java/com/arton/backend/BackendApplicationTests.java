package com.arton.backend;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void bucket4jTest() throws InterruptedException {
		// 1초마다 토큰 2개 충전
		Refill refill = Refill.intervally(2, Duration.ofSeconds(1));
		// 버킷 사이즈 3 큐 사이즈라 생각하면
		Bandwidth classic = Bandwidth.classic(3, refill);
		// 큐 3 1초마타 토큰 2개 충전 버킷 생성
		Bucket bucket = Bucket.builder().addLimit(classic).build();
		// test
		for (int i = 1; i <= 10; i++) {
			System.out.println("getAvailableTokens = " + bucket.getAvailableTokens());
			// consume
			if (bucket.tryConsume(1)) {
				System.out.println("idx = " + i);
			} else {
				System.out.println(" Token is Empty ");
				Thread.sleep(1000);
			}
		}
	}

	@Test
	void trafficTest() throws InterruptedException {
		// 1분에 5개의 요청만 허용
		Bandwidth minLimit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
		// 10초에 2개
		Bandwidth secLimit = Bandwidth.classic(2, Refill.intervally(2, Duration.ofSeconds(10)));
		// 2개 조합해서 생성
		Bucket bucket = Bucket.builder()
				.addLimit(minLimit)
				.addLimit(secLimit)
				.build();
		for (int i = 1; i <= 13; i++) {
			System.out.println("getAvailableTokens = " + bucket.getAvailableTokens());
			// consume
			if (bucket.tryConsume(1)) {
				System.out.println("idx = " + i);
			} else {
				System.out.println(" Token is Empty ");
				Thread.sleep(10000);
			}
		}
	}
}
