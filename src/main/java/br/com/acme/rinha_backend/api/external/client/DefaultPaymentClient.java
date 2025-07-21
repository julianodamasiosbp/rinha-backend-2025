package br.com.acme.rinha_backend.api.external.client;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.acme.rinha_backend.api.external.model.response.ServiceHealth;
import br.com.acme.rinha_backend.api.model.request.PaymentRequest;
import reactor.core.publisher.Mono;

@Service
public class DefaultPaymentClient {

    private final WebClient defaultPaymentProcessorClient;

    public DefaultPaymentClient(WebClient defaultPaymentProcessorClient) {
        this.defaultPaymentProcessorClient = defaultPaymentProcessorClient;
    }

// public Mono<HttpStatus> addPayment(PaymentRequest paymentRequest) {
//         System.out.println("DefaultPaymentClient: Attempting to add payment for order " + paymentRequest.getOrderId());

//         return defaultPaymentProcessorClient.post()
//                 .uri("/payments")
//                 .headers(headers -> {
//                     headers.setCacheControl(CacheControl.noCache().noStore().mustRevalidate());
//                     headers.setPragma("no-cache");
//                     headers.setExpires(0L);
//                     headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
//                 })
//                 .bodyValue(paymentRequest)
//                 .retrieve()
//                 // onStatus will now propagate an error signal for 4xx/5xx status codes.
//                 // This error will then be caught by the final onErrorResume.
//                 .onStatus(HttpStatus::isError, // Check for any error status (4xx or 5xx)
//                           clientResponse -> clientResponse.bodyToMono(String.class) // Optionally read error body
//                                   .flatMap(errorBody -> {
//                                       System.err.println("DefaultPaymentClient: Payment request failed with status " +
//                                                          clientResponse.statusCode() + ". Error body: " + errorBody);
//                                       // Propagate the specific HTTP status code as an error, which onErrorResume will catch
//                                       return Mono.error(new PaymentProcessingException(
//                                               "HTTP Status Error from processor",
//                                               clientResponse.statusCode(),
//                                               errorBody
//                                       ));
//                                   }))
//                 .toBodilessEntity() // Get the ResponseEntity without reading the body
//                 .map(ResponseEntity::getStatusCode) // Extract only the HttpStatus from the ResponseEntity
//                 .onErrorResume(throwable -> {
//                     // This catches any errors:
//                     // 1. Network issues (connection refused, timeout, host unreachable)
//                     // 2. Exceptions thrown by the onStatus handler (for 4xx/5xx responses)
//                     // 3. Any other unexpected runtime exceptions
//                     System.err.println("DefaultPaymentClient: Unhandled error during addPayment: " + throwable.getMessage());

//                     // Determine an appropriate status to return for a general failure
//                     if (throwable instanceof PaymentProcessingException) {
//                         // If it's an HTTP status error from the processor
//                         return Mono.just(((PaymentProcessingException) throwable).getHttpStatus());
//                     } else {
//                         // For network issues or other unhandled exceptions
//                         return Mono.just(HttpStatus.SERVICE_UNAVAILABLE); // Or INTERNAL_SERVER_ERROR
//                     }
//                 });
//     }


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
