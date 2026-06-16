package cz.cvut.fel.nss.product_service.repository;

import cz.cvut.fel.nss.product_service.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);
}