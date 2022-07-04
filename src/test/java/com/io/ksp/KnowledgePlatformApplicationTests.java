package com.io.ksp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class KnowledgePlatformApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void getTedTalk_returnTedTalk() {
        ResponseEntity<Page> response = restTemplate.getForEntity("/api/ksp/v1/ted-talks?title=good&page=0&size=100&views=475000", Page.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
