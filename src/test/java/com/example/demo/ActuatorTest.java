package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ActuatorTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String ACTUATOR_HEALTHCHECK_ENDPOINT = "http://localhost:8081/healthcheck";

    @Test
    void 액츄에이터로_헬스체크() {
        //when
        ResponseEntity<String> forEntity = testRestTemplate
            .getForEntity(ACTUATOR_HEALTHCHECK_ENDPOINT, String.class);

        //then
        assertEquals(forEntity.getStatusCode(), HttpStatus.OK);
    }
}
