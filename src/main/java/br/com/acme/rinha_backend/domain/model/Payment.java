package br.com.acme.rinha_backend.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.acme.rinha_backend.api.model.request.PaymentRequest;

@Document(collection = "payments")
public class Payment {

    @Id
    private UUID correlationId;

    private BigDecimal amount;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public Payment() {

    }

    public Payment(UUID correlationId, BigDecimal amount) {
        this.correlationId = correlationId;
        this.amount = amount;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Payment toModel(PaymentRequest paymentRequest) {
        return new Payment(paymentRequest.getCorrelationId(), paymentRequest.getAmount());
    }

}
