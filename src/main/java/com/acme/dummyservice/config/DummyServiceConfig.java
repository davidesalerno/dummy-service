package com.acme.dummyservice.config;

import com.acme.dummyservice.interfaces.NetworkUtils;
import com.acme.dummyservice.utils.ProxyNetwork;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DummyServiceConfig{
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public StopWatch stopWatch(){
        return new StopWatch("DummyService");
    }

    @Bean
    public NetworkUtils networkUtils(){return new ProxyNetwork();}
}
