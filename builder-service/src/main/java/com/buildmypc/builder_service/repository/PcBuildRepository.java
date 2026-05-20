package com.buildmypc.builder_service.repository;
import com.buildmypc.builder_service.model.PcBuild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcBuildRepository extends JpaRepository<PcBuild, Long> {
}