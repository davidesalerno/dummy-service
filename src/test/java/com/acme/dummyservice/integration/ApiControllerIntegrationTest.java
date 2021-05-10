package com.acme.dummyservice.integration;

import com.acme.dummyservice.DummyServiceApplication;
import com.acme.dummyservice.utils.WireMockHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(
        webEnvironment =  SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = DummyServiceApplication.class)
public class ApiControllerIntegrationTest {

    static WireMockHelper wireMockHelper = new WireMockHelper(38082);

    @BeforeAll
    static void beforeAll() throws IOException {
        wireMockHelper.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockHelper.shutdown();
    }

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

    @SneakyThrows
    @Test
    public void callToIndexWithBulkTo1ShouldProvideAResponse(){
        wireMockHelper.stubGetWithResource("/up1", "/test-data/upstream1.json");
        wireMockHelper.stubGetWithResource("/up2", "/test-data/upstream1.json");
        given().when().get("http://localhost:38080/?bulk=1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Dummy Service"))
                .body("uri",equalTo("/"))
                .body("type", equalTo("HTTP"))
                .body("$", hasKey("upstream_calls") )
                .body("upstream_calls.size", equalTo(2) );
    }

    @SneakyThrows
    @Test
    public void callToIndexWithBulkTo5ShouldProvideAResponse(){
        wireMockHelper.stubGetWithResourceAndQueryParams("/up1?bulk=5", "/test-data/upstream1.json");
        wireMockHelper.stubGetWithResourceAndQueryParams("/up2?bulk=5", "/test-data/upstream1.json");
        given().when().get("http://localhost:38080/?bulk=5")
                .then()
                .statusCode(200)
                .body("name", equalTo("Dummy Service"))
                .body("uri",equalTo("/"))
                .body("type", equalTo("HTTP"))
                .body("$", hasKey("upstream_calls") )
                .body("upstream_calls.size", equalTo(10) );
    }
}