package com.buildmypc.motherboard_service.repository;
import com.buildmypc.motherboard_service.model.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long>{
}
