package com.buildmypc.gpu_service.repository;
import com.buildmypc.gpu_service.model.Gpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Long> {
}
