package br.com.acme.rinha_backend.api.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.rinha_backend.domain.model.Payment;
import br.com.acme.rinha_backend.domain.repository.PaymentRepository;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public String paymentProcess(@RequestBody Payment payment) {
        Payment payment2 = new Payment();
        payment2.setCorrelationId(UUID.fromString("4a7901b8-7d26-4d9d-aa19-4dc1c7cf60b3"));
        payment2.setAmount(BigDecimal.valueOf(19.90));
        paymentRepository.save(payment2);
        return "Test";
    }

}
