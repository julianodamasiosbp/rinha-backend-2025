package br.com.acme.rinha_backend.api.external.client;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.acme.rinha_backend.api.external.model.response.ServiceHealth;
import reactor.core.publisher.Mono;

@Service
public class DefaultPaymentClient {

    private final WebClient defaultPaymentProcessorClient;

    public DefaultPaymentClient(WebClient defaultPaymentProcessorClient) {
        this.defaultPaymentProcessorClient = defaultPaymentProcessorClient;
    }

    public Mono<ServiceHealth> checkServiceHealth() {

        System.out.println(
                "DefaultPaymentClient: Checking health of default payment processor at its configured base URL and /payments/service-health endpoint");

        CacheControl.noCache();
        return defaultPaymentProcessorClient.get()
                .uri("/payments/service-health")
                .header(HttpHeaders.CACHE_CONTROL, CacheControl.noStore().mustRevalidate().getHeaderValue())
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(errorBody -> {
                                    System.err.println("DefaultPaymentClient: Health check failed with status " +
                                            clientResponse.statusCode() + ". Error body: " + errorBody);
                                    return new RuntimeException("Service health check failed");
                                }))
                .bodyToMono(ServiceHealth.class)
                .onErrorResume(throwable -> {
                    System.err.println(
                            "DefaultPaymentClient: Health check encountered an error: " + throwable.getMessage());
                    return Mono.just(new ServiceHealth());
                });
    }

}
