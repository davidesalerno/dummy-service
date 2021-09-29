# Dummy Service

Dummy Service for testing upstream service communications and testing service mesh and other scenarios, can operate only in HTTP.

- Package: https://github.com/davidesalerno/dummy-service/releases/
- Docker Images: https://hub.docker.com/repository/docker/davidesalerno/dummy-service

![main workflow](https://github.com/davidesalerno/dummy-service/actions/workflows/main.yml/badge.svg)
![release workflow](https://github.com/davidesalerno/dummy-service/actions/workflows/release.yml/badge.svg)

## Configuration
Configuration values are set using environment variables, for info please see the following list:

```
Configuration values are set using environment variables, for info please see the following list:

Environment variables:

    NAME default: 'Dummy Service'
        Name of the service
    MESSAGE  default: 'Hello World!'
        Message to be returned from service, can either be a string or valid JSON
    SERVER_PORT  default: 8080
        Port to bind the service to
    SERVER_ADDRESS  default: '0.0.0.0'
        IP address the service is listening to
    UPSTREAM_URIS  default: no default
        Comma separated URIs of the upstream services to call
    TIMING_WAIT_BEFORE_RESPONSE  default: -1L
        Long value that represents the numers of ms that the api waits before sending the response
```

N.B This service is developed using Spring and in particular Spring Boot, so you can refer to the [official framework documentation](https://docs.spring.io/spring-boot/docs/2.4.5/reference/html/appendix-application-properties.html#common-application-properties) for the additional environment variables not reported above.

## UI
Dummy Service also has a handy dandy UI which can be used to graphically represent the data which is returned as JSON when curling.

The API is accessible at the path `/ui` and under the covers just calls the main API endpoint.

If you would like to perform more than one call to the upstream services you can go in the Advanced page of the UI.

## Credits
- Inspired by https://github.com/nicholasjackson/fake-service