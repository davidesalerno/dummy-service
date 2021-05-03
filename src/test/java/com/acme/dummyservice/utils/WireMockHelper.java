package com.acme.dummyservice.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;

public class WireMockHelper {
    WireMockServer wireMockServer;

    public WireMockHelper(int port) {
        this.wireMockServer = new WireMockServer(port);
    }

    public void start() {
        wireMockServer.start();
    }

    public void shutdown() {
        wireMockServer.stop();
    }

    public String getUrl(){
        return wireMockServer.baseUrl();
    }

    @SneakyThrows
    public void stubGetWithResource(String url, String resource) {
        stubGetWithString(url, ResourceUtil.getResource(resource));
    }

    @SneakyThrows
    public void stubGetWithResourceAndQueryParams(String url, String resource) {
        stubGetWithStringAndQueryParams(url, ResourceUtil.getResource(resource));
    }


    public void stubGetWithStringAndQueryParams(String url, String data) {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo(url))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("content-type", "application/json")
                                .withBody(data)
                        ));
    }

    public void stubGetWithString(String url, String data) {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlPathEqualTo(url))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("content-type", "application/json")
                                .withBody(data)
                        ));
    }

    public void stubGetWith404(String url){
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo(url))
                        .willReturn(WireMock.aResponse()
                                .withStatus(404)
                        ));
    }

    public void stubPostWithJson(String url, String data) {
        stubPostWithJson(url, data, 200);
    }

    public void stubPostWithJson(String url, String data, Integer statusCode) {
        wireMockServer.stubFor(
                WireMock.post(WireMock.urlPathEqualTo(url))
                        .withRequestBody(equalToJson(data))
                        .willReturn(WireMock.aResponse()
                                .withStatus(statusCode)
                                .withHeader("Content-Type", "application/json")
                        ));
    }

    public void stubPostWithJson(String url, String data, Integer statusCode, HashMap<String,String> headersMap) {
        headersMap.putIfAbsent("Content-Type", "application/json");
        HttpHeaders headers = new HttpHeaders(generateHTTPHeaders(headersMap));
        wireMockServer.stubFor(
                WireMock.post(WireMock.urlPathEqualTo(url))
                        .withRequestBody(equalToJson(data,true, true))
                        .willReturn(WireMock.aResponse()
                                .withHeaders(headers)
                                .withStatus(statusCode)
                        ));
    }

    private  List<HttpHeader> generateHTTPHeaders(HashMap<String, String> headersMap) {
        return headersMap.entrySet().stream().map(e -> new HttpHeader(e.getKey(), e.getValue())).filter(Objects::nonNull).collect(Collectors.toList());
    }

}


