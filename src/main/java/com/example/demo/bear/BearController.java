package com.example.demo.bear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")
public class BearController {
    
    private static final Logger logger = LoggerFactory.getLogger(BearController.class);
    
    @Autowired
    private BearService bearService;
    
    @GetMapping("/bears")
    public Object getAllBears() {
        logger.info("GET /bears - Fetching all bears");
        return bearService.getAllBears();
    }
    
    @GetMapping("/bears/{bearId}")
    public Bear getBearById(@PathVariable Long bearId) {
        logger.info("GET /bears/{} - Fetching bear by ID", bearId);
        return bearService.getBearById(bearId);
    }
    
    @PostMapping("/bears")
    public Object addBear(@Valid @RequestBody Bear bear) {
        logger.info("POST /bears - Adding new bear: name={}, description={}, age={}, habitat={}, species={}, type={}, imageUrl={}", 
            bear.getBearName(), bear.getBearDescription(), bear.getAge(), bear.getHabitat(), 
            bear.getSpecies(), bear.getType(), bear.getImageUrl());
        return bearService.addBear(bear);
    }
    
    @PutMapping("/bears/{bearId}")
    public Bear updateBear(@PathVariable Long bearId, @Valid @RequestBody Bear bear) {
        logger.info("PUT /bears/{} - Updating bear: name={}, description={}, age={}, habitat={}, species={}, type={}, imageUrl={}", 
            bearId, bear.getBearName(), bear.getBearDescription(), bear.getAge(), bear.getHabitat(),
            bear.getSpecies(), bear.getType(), bear.getImageUrl());
        return bearService.updateBear(bearId, bear);
    }
    
    @DeleteMapping("/bears/{bearId}")
    public void deleteBear(@PathVariable Long bearId) {
        logger.info("DELETE /bears/{} - Deleting bear", bearId);
        bearService.deleteBear(bearId);
    }
    
    @GetMapping("/bears/habitat/{habitat}")
    public List<Bear> getBearsByHabitat(@PathVariable String habitat) {
        logger.info("GET /bears/habitat/{} - Fetching bears by habitat", habitat);
        return bearService.getBearsByHabitat(habitat);
    }
    
    @GetMapping("/bears/search/{name}")
    public List<Bear> getBearsByName(@PathVariable String name) {
        logger.info("GET /bears/search/{} - Searching bears by name", name);
        return bearService.getBearsByName(name);
    }
    
}
