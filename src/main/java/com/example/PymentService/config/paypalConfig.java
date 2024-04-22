package com.example.PymentService.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class paypalConfig {
    @Value("${paypal.client.id}")
    private String client_id ;
    @Value("${paypal.client.secret}")
    private String client_secret ;
    @Value("${paypal.client.mode}")
    private String mode ;
     @Bean
    public APIContext getAPIContext() {
         return new APIContext(client_id, client_secret, mode);
    }
}
