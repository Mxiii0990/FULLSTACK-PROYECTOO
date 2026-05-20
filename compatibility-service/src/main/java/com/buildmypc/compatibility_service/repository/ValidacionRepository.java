package com.buildmypc.compatibility_service.repository;

import com.buildmypc.compatibility_service.model.ValidacionCompatibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidacionRepository extends JpaRepository<ValidacionCompatibilidad, Long> {
}