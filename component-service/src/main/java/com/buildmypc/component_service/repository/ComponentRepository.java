package com.buildmypc.component_service.repository;

import com.buildmypc.component_service.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long>{

}
