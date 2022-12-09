package com.example.demo.repository;

import com.example.demo.domain.IntervaloDePremios;
import com.example.demo.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, UUID> {
}
