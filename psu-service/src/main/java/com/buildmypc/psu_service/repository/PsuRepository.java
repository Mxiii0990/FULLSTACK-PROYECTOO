package com.buildmypc.psu_service.repository;
import com.buildmypc.psu_service.model.Psu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsuRepository extends JpaRepository<Psu, Long>{
}
