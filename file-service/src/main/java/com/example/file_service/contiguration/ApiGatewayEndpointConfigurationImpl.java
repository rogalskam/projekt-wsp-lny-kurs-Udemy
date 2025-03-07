package com.example.file_service.contiguration;

import jakarta.annotation.PostConstruct;
import org.javaudemy.ApiGatewayEndpointConfiguration;
import org.javaudemy.entity.Endpoint;
import org.javaudemy.entity.HttpMethod;
import org.javaudemy.entity.Role;
import org.javaudemy.entity.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiGatewayEndpointConfigurationImpl implements ApiGatewayEndpointConfiguration {
    @Value("${api-gateway.url}")
    private String GATEWAY_URL;

    @PostConstruct
    public void startrOperation(){
        initMap();
        register();
    }

    @Override
    public void initMap() {
        endpointList.add(new Endpoint("/api/v1/image", HttpMethod.GET, Role.GUEST));
        endpointList.add(new Endpoint("/api/v1/image", HttpMethod.POST, Role.GUEST));
        endpointList.add(new Endpoint("/api/v1/image", HttpMethod.PATCH, Role.GUEST));
        endpointList.add(new Endpoint("/api/v1/image", HttpMethod.DELETE, Role.ADMIN));

    }

    @Override
    public void register() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Response> response = restTemplate.postForEntity(GATEWAY_URL, endpointList, Response.class);
        if (response.getStatusCode().isError()) throw new RuntimeException();
    }
}