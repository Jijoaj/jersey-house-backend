package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.OrderPlacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderPlacementRepository extends JpaRepository<OrderPlacement, String> {
    Optional<OrderPlacement> findFirstByOrderStatusOrderByOrderPlacedDate(String orderStatus);
}