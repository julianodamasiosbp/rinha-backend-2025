package br.com.acme.rinha_backend.api.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final String DEFAULT_PAYMENT_PROCESSOR_BASE_URL = "http://payment-processor-default:8080";
    private static final String FALLBACK_PAYMENT_PROCESSOR_BASE_URL = "http://payment-processor-fallback:8080";

    @Bean
    public WebClient defaultPaymentProcessorClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(DEFAULT_PAYMENT_PROCESSOR_BASE_URL)
                               .build();
    }

    @Bean
    public WebClient fallbackPaymentProcessorClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(FALLBACK_PAYMENT_PROCESSOR_BASE_URL)
                               .build();
    }
}

