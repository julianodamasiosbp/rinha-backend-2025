package br.com.acme.rinha_backend.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.rinha_backend.api.model.request.PaymentRequest;
import br.com.acme.rinha_backend.domain.model.Payment;
import br.com.acme.rinha_backend.domain.repository.PaymentRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void paymentProcess(@RequestBody @Valid PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        Payment paymentToBeSaved = payment.toModel(paymentRequest);
        this.paymentRepository.save(paymentToBeSaved);
    }
}
