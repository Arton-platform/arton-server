package com.arton.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		String s = LocalDate.now().toString().replaceAll("-",".");
		System.out.println("s = " + s);
	}

}
