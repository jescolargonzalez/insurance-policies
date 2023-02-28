package com.tfm.aseguradora.backend.tfm.policy.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
public class RestClientConfiguration {

    @Value("${rest.client.core.users.host}")
    private String baseUsersHost;

    @Bean
    @Primary
    public com.tfm.aseguradora.backend.middle.users.ApiClient securityClient(
            @Autowired @Qualifier("restTemplateJwtPropagator") RestTemplate restTemplate) {
        var userApiClient = new com.tfm.aseguradora.backend.middle.users.ApiClient(restTemplate);
        userApiClient.setBasePath(baseUsersHost);
        return userApiClient;
    }


    @Bean("restTemplateJwtPropagator")
    public RestTemplate restTemplate() {
        var restTemplate = new RestTemplate();
        var restTemplateInterceptors = restTemplate.getInterceptors();
        restTemplateInterceptors.add(new RestClientJwtInterceptor());
        return restTemplate;
    }
}