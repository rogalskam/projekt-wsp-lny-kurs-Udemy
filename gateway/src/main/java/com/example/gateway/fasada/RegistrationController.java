package com.example.gateway.fasada;

import com.example.gateway.filter.RouteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.javaudemy.entity.Endpoint;
import org.javaudemy.entity.Response;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/gateway")
@RequiredArgsConstructor
public class RegistrationController {
    private final RouteValidator routeValidator;

    @PostMapping
    public ResponseEntity<Response> register(@RequestBody List<Endpoint> endpointList) {
        routeValidator.addEndpoints(endpointList);
        return ResponseEntity.ok(new Response("Successfully register new endpoints"));
    }
}
