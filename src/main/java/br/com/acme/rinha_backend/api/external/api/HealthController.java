package br.com.acme.rinha_backend.api.external.api;

import org.springframework.context.annotation.Fallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.rinha_backend.api.external.client.DefaultPaymentClient;
import br.com.acme.rinha_backend.api.external.client.FallbackPaymentClient;
import br.com.acme.rinha_backend.api.external.model.response.ServiceHealth;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/external/health")
public class HealthController {

     private final DefaultPaymentClient defaultPaymentClient;
     private final FallbackPaymentClient fallbackPaymentClient;

    public HealthController(DefaultPaymentClient defaultPaymentClient, FallbackPaymentClient fallbackPaymentClient) {
        this.defaultPaymentClient = defaultPaymentClient;
        this.fallbackPaymentClient = fallbackPaymentClient;
    }

    @GetMapping("/default-payment-processor")
    public Mono<ServiceHealth> getDefaultPaymentProcessorHealth() {
        for (int i = 0; i < 10; i++) {
            Mono<ServiceHealth> checkServiceHealth = defaultPaymentClient.checkServiceHealth();
            System.out.println("HealthController: Checking health of default payment processor, attempt " + (i + 1));
            System.out.println(checkServiceHealth);
        }
        return defaultPaymentClient.checkServiceHealth();
    }

    @GetMapping("/fallback-payment-processor")
    public Mono<ServiceHealth> getFallbackPaymentProcessorHealth() {
        return fallbackPaymentClient.checkServiceHealth();
    }

}
