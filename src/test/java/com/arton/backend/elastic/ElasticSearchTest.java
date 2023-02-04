package com.arton.backend.elastic;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ElasticTest.class)
@SpringBootTest
public class ElasticSearchTest {
}
