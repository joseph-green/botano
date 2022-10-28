package com.botano.api;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.botano.api.repos.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.botano.api.models.Plant;

@RestController
public class PlantsController {

    @Autowired
    PlantRepository plantRepository;

    @GetMapping("/plants")
    public ResponseEntity<List<Plant>> index() {
        try {
            return new ResponseEntity<>(plantRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/plants/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable UUID id) {

        try {
            Optional<Plant> _plant = plantRepository.findById(id);

            return new ResponseEntity<>(_plant.orElse(null),
                    _plant.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);

        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @PostMapping("/plants")
    public ResponseEntity<Plant> newPlant(@RequestBody Plant plant) {
        try {
            Plant _plant = new Plant(plant.getCommonName(),plant.getScientificName());
            plantRepository.save(_plant);
            return new ResponseEntity<>(_plant, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/plants/{id}")
    public ResponseEntity<Void> updatePlant(@PathVariable UUID id, @RequestBody Plant plant) {

        try {
            Optional<Plant> _existingPlant = plantRepository.findById(id);
            Plant _plant;
            if (_existingPlant.isPresent()) {
                _plant = _existingPlant.get();
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            _plant.setCommonName(plant.getCommonName());
            _plant.setScientificName(plant.getScientificName());

            plantRepository.save(plant);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/plants/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable UUID id) {

        try {
            plantRepository.deleteById(id);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}