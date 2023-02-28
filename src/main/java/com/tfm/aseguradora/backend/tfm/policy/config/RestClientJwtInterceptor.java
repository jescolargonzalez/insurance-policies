package com.tfm.aseguradora.backend.tfm.policy.config;

import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.security.core.context.*;

import java.io.*;

public class RestClientJwtInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (request.getURI().toString().contains("/users")) {
            return execution.execute(request, body);
        }
        else {
            var token = SecurityContextHolder.getContext().getAuthentication();
            request.getHeaders().add("Authorization", token.getName());
            return execution.execute(request, body);
        }
    }

}