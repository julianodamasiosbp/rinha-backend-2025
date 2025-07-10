package br.com.acme.rinha_backend.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.acme.rinha_backend.domain.model.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, UUID> {

    List<Payment> findByEventDateBetween(OffsetDateTime from, OffsetDateTime to);

}
