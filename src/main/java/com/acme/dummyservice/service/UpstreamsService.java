package com.acme.dummyservice.service;

import com.acme.dummyservice.dto.ResponseDTO;
import com.acme.dummyservice.utils.Either;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UpstreamsService {

    private final RestTemplate restClient;

    public UpstreamsService(RestTemplate restClient){
        this.restClient = restClient;
    }

    public List<ResponseDTO> call(String upstreamUris, Integer bulkCalls){
        return (upstreamUris!=null && !upstreamUris.isEmpty() && !upstreamUris.isBlank()) ? Arrays.stream(upstreamUris.split(";")).map(uri -> callService(buildUri(uri, bulkCalls), bulkCalls)).flatMap(List::stream).collect(Collectors.toList()) : null;
    }

    private String buildUri(String uri, Integer bulkCalls) {
        return (bulkCalls > 1) ? uri + "?bulk=" + bulkCalls : uri;
    }

    public List<ResponseDTO> call(String upstreamUris){
        return call(upstreamUris, 1);
    }

    private ResponseDTO callService(String uri){
        return restClient.getForObject(uri, ResponseDTO.class);
    }

    private List<ResponseDTO> callService(String uri, Integer bulkCalls){
        return Stream.iterate(0, i -> i).limit(bulkCalls).map(Either.liftWithValue(o -> restClient.getForObject(uri, ResponseDTO.class))).filter(v->v.isRight()).map(r -> r.getRight()).filter(o -> o.isPresent()).map(r -> (ResponseDTO) r.get()).collect(Collectors.toList());
    }
}
