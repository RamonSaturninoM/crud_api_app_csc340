package com.example.demo.bear;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "bears")
public class Bear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bearId;

    @Column(nullable = false)
    @NotBlank(message = "Bear name is required")
    private String bearName;

    @Column(nullable = false)
    @NotBlank(message = "Bear description is required")
    private String bearDescription;

    @Column(name = "age")
    @Min(value = 0, message = "Age must be non-negative")
    private int age;
    
    @Column(name = "habitat")
    @NotBlank(message = "Habitat is required")
    private String habitat;
    

    public Bear() {       
    }        

    public Bear(Long bearId, String bearName, String bearDescription, int age, String habitat) {
        this.bearId = bearId;
        this.bearName = bearName;
        this.bearDescription = bearDescription;
        this.age = age;
        this.habitat = habitat;
    }

    public Bear(String bearName, String bearDescription, int age, String habitat) {
        this.bearName = bearName;
        this.bearDescription = bearDescription;
        this.age = age;
        this.habitat = habitat;
    }

    public Long getBearId() {
        return bearId;
    }

    public void setBearId(Long bearId) {
        this.bearId = bearId;
    }

    public String getBearName() {
        return bearName;
    }

    public void setBearName(String bearName) {
        this.bearName = bearName;
    }

    public String getBearDescription() {
        return bearDescription;
    }

    public void setBearDescription(String bearDescription) {
        this.bearDescription = bearDescription;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
}
