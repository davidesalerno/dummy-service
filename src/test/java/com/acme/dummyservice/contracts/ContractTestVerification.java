package com.acme.dummyservice.contracts;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.acme.dummyservice.utils.WireMockHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("dummyservice-api")
@PactFolder("src/test/resources/pacts")
public class ContractTestVerification {

    static WireMockHelper wireMockHelper = new WireMockHelper(38082);

    @BeforeAll
    static void beforeAll() throws IOException {
        wireMockHelper.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockHelper.shutdown();
    }

    @LocalServerPort
    private int port;

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State("dummy service simply works")
    public void toDefaultState() {
        wireMockHelper.stubGetWithResource("/up1", "/test-data/upstream1.json");
        wireMockHelper.stubGetWithResource("/up2", "/test-data/upstream1.json");
        System.out.println("E");
    }

}
