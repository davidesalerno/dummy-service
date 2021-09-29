package com.acme.dummyservice.service;

import com.acme.dummyservice.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ResponseService {
    private Long waitForBeforeResponseMs = -1L;

    @Value("${timing.wait.before.response:-1}")
    public void setWaitForBeforeResponseMs(Long waitForBeforeResponseMs) {
        this.waitForBeforeResponseMs = waitForBeforeResponseMs;
    }

    public ResponseDTO buildIndexResponse(String path, ResponseDTO response, List<ResponseDTO> upstreamResponses, List<String> iPv4Addresses, Date startTime) {
        if(upstreamResponses!=null && !upstreamResponses.isEmpty()){
            response.setUpstreamCalls(upstreamResponses);
        }
        Date endTime =  new Date();
        response.setUri(path);
        response.setDuration(endTime.getTime()-startTime.getTime());
        response.setStartTime(new Timestamp(startTime.getTime()));
        response.setEndTime(new Timestamp(endTime.getTime()));
        response.setIpAddresses(iPv4Addresses);
        response.setCode(HttpStatus.OK.value());
        if(waitForBeforeResponseMs >0L){
            try {
                Thread.sleep(waitForBeforeResponseMs);
            } catch (InterruptedException e) {
                log.warn("Exception caught during sleep before response");
            }
        }
        return  response;
    }


}
