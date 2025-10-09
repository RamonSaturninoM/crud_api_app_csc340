package com.example.demo.bear;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BearRepository extends JpaRepository<Bear, Long> {
    List<Bear> findByBearName(String bearName);

    @Query(value = "select * from bears where habitat = :habitat", nativeQuery = true)
    List<Bear> findByHabitat(String habitat);

    @Query(value = "select * from bears where age = :age", nativeQuery = true)
    List<Bear> findByAge(int age);

}
