package com.buildmypc.component_service.repository;

import com.buildmypc.component_service.model.Componente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long>{

}
