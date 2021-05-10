package com.acme.dummyservice.controller;

import com.acme.dummyservice.dto.ResponseDTO;
import com.acme.dummyservice.service.NetworkService;
import com.acme.dummyservice.service.ResponseService;
import com.acme.dummyservice.service.UpstreamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@RestController
public class ApiController {

    private final UpstreamsService upstreamService;
    private final NetworkService networkService;
    private final ResponseService responseService;
    private String name;
    private String message;
    private String upstreamServices;

    public ApiController(ResponseService responseService, UpstreamsService upstreamServices, NetworkService networkService){
        this.responseService = responseService;
        this.upstreamService = upstreamServices;
        this.networkService = networkService;
    }
    
    @Value("${name:Dummy Service}")
    public void setName(String name){
        this.name = name;
    }

    @Value("${message:Hello world!}")
    public void setMessage(String message){
        this.message = message;
    }

    @Value("${upstreams.calls:#{null}}")
    public void setUpstreamServices(String upstreamServices){
        this.upstreamServices = upstreamServices;
    }

    @Operation(summary = "Get a response from Dummy Service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dummy Service is working well",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input supplied",
                    content = @Content) })
    @RequestMapping(value = {"/", "/{spring:\\b(?!(?:ui)\\b)\\w+}","/**/{spring:\\b(?!(?:ui)\\b)\\w+}","/{spring:\\b(?!(?:ui)\\b)\\w+}/**{spring:?!(\\.js|\\.css)$}"})
    public ResponseDTO index(@RequestParam(name = "bulk", required = false, defaultValue = "1") Integer bulkCalls, HttpServletRequest request) throws SocketException {
        return responseService.buildIndexResponse(request.getRequestURI().substring(request.getContextPath().length()), ResponseDTO.builder().name(this.name).body(this.message).build(), upstreamService.call(upstreamServices, bulkCalls), networkService.getIPv4Addresses(), new Date());
    }

}
