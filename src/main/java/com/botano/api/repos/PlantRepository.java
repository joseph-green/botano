package com.botano.api.repos;

import com.botano.api.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, UUID> {
    List<Plant> findByScientificName(String scientificName);
}
