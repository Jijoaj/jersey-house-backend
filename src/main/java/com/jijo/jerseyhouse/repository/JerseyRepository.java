package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.Jersey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JerseyRepository extends JpaRepository<Jersey, Integer> {
}