package br.com.acme.rinha_backend.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {

    private UUID correlationId;
    private BigDecimal amount;

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

}
