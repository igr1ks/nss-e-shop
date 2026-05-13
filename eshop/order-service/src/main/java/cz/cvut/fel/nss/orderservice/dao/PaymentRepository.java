package cz.cvut.fel.nss.orderservice.dao;

import cz.cvut.fel.nss.orderservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}