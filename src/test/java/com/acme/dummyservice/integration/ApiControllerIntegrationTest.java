package com.acme.dummyservice.integration;

import com.acme.dummyservice.DummyServiceApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(
        webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = DummyServiceApplication.class)
public class ApiControllerIntegrationTest {

    @SneakyThrows
    @Test
    public void callToIndexShouldProvideAResponse(){
        given().when().get("http://localhost:38080/")
                .then()
                .statusCode(200)
                .body("name", equalTo("Dummy Service"))
                .body("uri",equalTo("/"))
                .body("type", equalTo("HTTP"));
    }
}