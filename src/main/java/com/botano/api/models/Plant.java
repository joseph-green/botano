package com.botano.api.models;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plants")
public class Plant {

    private @Id UUID id = UUID.randomUUID();
    private String commonName;
    private String scientificName;

    public Plant() {}
    public Plant(String commonName, String scientificName) {
        this.commonName = commonName;
        this.scientificName = scientificName;
    }

    public UUID getId() {
        return this.id;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

}
