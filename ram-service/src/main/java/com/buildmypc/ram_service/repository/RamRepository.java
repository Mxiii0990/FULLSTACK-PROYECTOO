package com.buildmypc.ram_service.repository;
import com.buildmypc.ram_service.model.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RamRepository extends JpaRepository<Ram, Long>{
}
