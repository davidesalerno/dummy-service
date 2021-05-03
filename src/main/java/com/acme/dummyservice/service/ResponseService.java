package com.acme.dummyservice.service;

import com.acme.dummyservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ResponseService {
    public ResponseDTO buildIndexResponse(String path, ResponseDTO response, List<ResponseDTO> upstreamResponses, List<String> iPv4Addresses, Date startTime ) {
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
        return  response;
    }
}
