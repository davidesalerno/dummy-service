package com.acme.dummyservice.service;

import com.acme.dummyservice.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpstreamsService {

    private final RestTemplate restClient;

    public UpstreamsService(RestTemplate restClient){
        this.restClient = restClient;
    }

    public List<ResponseDTO> call(String upstreamUris){
        return (upstreamUris!=null && !upstreamUris.isEmpty() && !upstreamUris.isBlank()) ? Arrays.stream(upstreamUris.split(";")).map(uri -> callService(uri)).collect(Collectors.toList()) : null;
    }

    private ResponseDTO callService(String uri){
        return restClient.getForObject(uri, ResponseDTO.class);
    }
}
