package com.example.demo.bear;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bears")
public class Bear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bearId;

    @Column(nullable = false)
    private String bearName;

    @Column(nullable = false)
    private String bearDescription;

    @Column(name = "age")
    private int age;
    
    @Column(name = "habitat")
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
