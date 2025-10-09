package com.example.demo.bear;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BearController {
    
    @Autowired
    private BearService bearService;
    
    @GetMapping("/bears")
    public Object getAllBears() {
        return bearService.getAllBears();
    }
    @GetMapping("/bears/{bearId}")
    public Bear getBearById(@PathVariable Long bearId) {
        return bearService.getBearById(bearId);
    }
    @PostMapping("/bears")
    public Bear addBear(@RequestBody Bear bear) {
        return bearService.addBear(bear);
    }
    @PutMapping("/bears/{bearId}")
    public Bear updateBear(@PathVariable Long bearId, @RequestBody Bear bear) {
        return bearService.updateBear(bearId, bear);
    }
    @DeleteMapping("/bears/{bearId}")
    public void deleteBear(@PathVariable Long bearId) {
        bearService.deleteBear(bearId);
    }
    @GetMapping("/bears/habitat/{habitat}")
    public List<Bear> getBearsByHabitat(@PathVariable String habitat) {
        return bearService.getBearsByHabitat(habitat);
    }
    @GetMapping("/bears/search/{name}")
    public List<Bear> getBearsByName(@PathVariable String name) {
        return bearService.getBearsByName(name);
    }
    
}
