package br.com.acme.rinha_backend.api.controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.rinha_backend.domain.model.Payment;
import br.com.acme.rinha_backend.domain.repository.PaymentRepository;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/payments-summary")
public class PaymentSummaryController {

    private PaymentRepository paymentRepository;

    public PaymentSummaryController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public ResponseEntity<String> getPaymentSummary(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        System.out.println(from);
        System.out.println(to);

        if (to.isBefore(from)) {
            return ResponseEntity.badRequest().body("The 'From' date must be less than or equal to the 'To' date.");
        }
        List<Payment> byEventDateBetween = this.paymentRepository.findByCreatedAtBetween(from, to);

        System.out.println(byEventDateBetween);

        if (byEventDateBetween.size() > 0) {
            byEventDateBetween.forEach((payment) -> System.out.println(payment));
        }
        return ResponseEntity.ok().body("payment-summary");
    }

}
