package com.acme.dummyservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class ResponseDTO {

    public enum ServiceType {
        HTTP,
        GRPC
    }

    private String name;
    private String uri;
    private final ServiceType type = ServiceType.HTTP;
    private String body;
    private Long duration;
    @JsonProperty("upstream_calls")
    private List<ResponseDTO> upstreamCalls;
    private Integer code;
    @JsonProperty("start_time")
    private Timestamp startTime;
    @JsonProperty("end_time")
    private Timestamp endTime;
    @JsonProperty("ip_addresses")
    private List<String> ipAddresses;


}
