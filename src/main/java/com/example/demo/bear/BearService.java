package com.example.demo.bear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class BearService {

    @Autowired
    private BearRepository bearRepository;

    public Object getAllBears() {
        return bearRepository.findAll();
    }
    public Bear getBearById(@PathVariable Long bearId) {
        return bearRepository.findById(bearId).orElse(null);
    }
    public Bear addBear(Bear bear) {
        return bearRepository.save(bear);
    }
    public Bear updateBear(Long bearId, Bear bear) {
        return bearRepository.save(bear);
    }
    public void deleteBear(Long bearId) {
        bearRepository.deleteById(bearId);
    }
    public List<Bear> getBearsByHabitat(String habitat) {
        return bearRepository.findByHabitat(habitat);
    }
    public List<Bear> getBearsIfNameContains(String name) {
        return bearRepository.findByBearNameContaining(name);
    }

}
