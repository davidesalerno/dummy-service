package com.acme.dummyservice.service;

import com.acme.dummyservice.dto.ResponseDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


class ResponseServiceTest {

    private ResponseService serviceUnderTest = new ResponseService();
    @SneakyThrows
    @Test
    public void givenAValidRequestShouldProvideAValidResponseDTO(){
        ResponseDTO response = serviceUnderTest.buildIndexResponse("/", ResponseDTO.builder().name("Dummy Service under Test").body("Hello Test!").build(), null, Arrays.asList("127.0.0.0"), new Date());
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Dummy Service under Test");
        assertThat(response.getBody()).isEqualTo("Hello Test!");
        assertThat(response.getUri()).isEqualTo("/");
    }

    @SneakyThrows
    @Test
    public void givenAValidRequestShouldResponseAfterTheSleep(){
        long timeToSleep = 15000L;
        serviceUnderTest.setWaitForBeforeResponseMs(timeToSleep);
        long start = System.currentTimeMillis();
        serviceUnderTest.buildIndexResponse("/", ResponseDTO.builder().name("Dummy Service under Test").body("Hello Test!").build(), null, Arrays.asList("127.0.0.0"), new Date());
        long end = System.currentTimeMillis();
        assertThat(end-start).isGreaterThanOrEqualTo(timeToSleep);
        serviceUnderTest.setWaitForBeforeResponseMs(-1L);
    }
}