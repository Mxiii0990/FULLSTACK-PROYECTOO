package com.buildmypc.cpu_service.repository;
import com.buildmypc.cpu_service.model.Cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CpuRepository extends JpaRepository<Cpu, Long>{
}
