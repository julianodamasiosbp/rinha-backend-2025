package br.com.acme.rinha_backend.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.rinha_backend.domain.model.Payment;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping
    public String paymentProcess(@RequestBody Payment payment) {
        return "Test";
    }

}
