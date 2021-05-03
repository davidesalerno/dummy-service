package com.acme.dummyservice.service;

import com.acme.dummyservice.dto.ResponseDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpstreamsServiceTest {
    RestTemplate restClient = mock(RestTemplate.class);
    private UpstreamsService serviceUnderTest = new UpstreamsService(restClient);

    @SneakyThrows
    @Test
    public void givenAValidRequestShouldProvideAValidResponseDTO(){
        ResponseDTO sampleResp = ResponseDTO.builder().name("Another Dummy Service").body("Hello Service Test!").uri("/").duration(100L).build();
        when(restClient.getForObject("http://localhost:8081", ResponseDTO.class)).thenReturn(sampleResp);
        List<ResponseDTO> response = serviceUnderTest.call("http://localhost:8081");
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0)).isEqualTo(sampleResp);
    }

}