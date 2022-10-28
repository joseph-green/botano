package com.botano.api;

import com.botano.api.models.Plant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ALL")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getPlants() {
        ResponseEntity<List> response = template.getForEntity("/plants", List.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isNotZero();
        assertThat(response.getBody().size()).isNotZero();
    }

    @Test
    public void getPlantById() {
        UUID _id = UUID.fromString("4fc88201-fbae-4028-b582-0d02f2af2b5e");
        ResponseEntity<Plant> response = template.getForEntity("/plants/{id}", Plant.class, _id);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(_id);

    }

    @Test
    public void newPlant() {
        Plant _plant = new Plant("Green Plant","Namus Scientifica");
        ResponseEntity<Plant> response = template.postForEntity("/plants", _plant, Plant.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

    }

    @Test
    public void updatePlant() {
        Plant _plant = new Plant("Green Plant","Namus Scientifica");
        ResponseEntity<Plant> postResponse = template.postForEntity("/plants", _plant, Plant.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getId()).isNotNull();

        UUID _id = postResponse.getBody().getId();
        String newCommonName = "Red Plant";
        String newScientificName = "Namus Alternativa";

        _plant.setCommonName(newCommonName);
        _plant.setScientificName(newScientificName);
        template.put("/plants/{id}", _plant, _id);

        ResponseEntity<Plant> getIdResponse = template.getForEntity("/plants/{id}", Plant.class, _id);
        assertThat(getIdResponse.getBody()).isNotNull();
        assertThat(getIdResponse.getBody().getId()).isEqualTo(_id);
        assertThat(getIdResponse.getBody().getCommonName()).isEqualTo(newCommonName);
        assertThat(getIdResponse.getBody().getScientificName()).isEqualTo(newScientificName);

    }

    @Test
    public void deletePlant() {
        Plant _plant = new Plant("Green Plant","Namus Scientifica");
        ResponseEntity<Plant> postResponse = template.postForEntity("/plants", _plant, Plant.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        assertThat(postResponse.getBody().getId()).isNotNull();

        UUID _id = postResponse.getBody().getId();
        template.delete("/plants/{id}", _id);

        ResponseEntity<Plant> getIdResponse = template.getForEntity("/plants/{id}", Plant.class, _id);
        assertThat(getIdResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
}