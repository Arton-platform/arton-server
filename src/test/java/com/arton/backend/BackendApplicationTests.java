package com.arton.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		String s = LocalDate.now().toString().replaceAll("-",".");
		System.out.println("s = " + s);
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString();
		System.out.println("now = " + now);
		String after = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString();
		System.out.println("after = " + after);
		String base = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm '기준'")).toString();
		System.out.println("now = " + base);
	}

	@Test
	void listToArray() {
		List<String> arr = new ArrayList<>();
		arr.add("Aa");
		arr.add("BB");
		String[] strings = arr.toArray(new String[arr.size()]);
		for (String string : strings) {
			System.out.println("string = " + string);
		}
	}
}
