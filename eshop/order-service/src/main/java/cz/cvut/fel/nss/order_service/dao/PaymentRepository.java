package cz.cvut.fel.nss.order_service.dao;

import cz.cvut.fel.nss.order_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}