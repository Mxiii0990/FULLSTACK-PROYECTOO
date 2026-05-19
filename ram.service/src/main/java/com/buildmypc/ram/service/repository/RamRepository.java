package com.buildmypc.ram.service.repository;
import com.buildmypc.ram.service.model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepository extends JpaRepository<Ram, Long>{
}
