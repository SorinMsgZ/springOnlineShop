package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.LocationDTO;
import ro.msg.learning.shop.services.LocationService;

import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService service;

    @GetMapping("/locations")
    public List<LocationDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/locations/{name}")
    public LocationDTO readByName(@PathVariable String name) {
        return service.readByName(name);
    }

    @PostMapping("/locations")
    public LocationDTO create(@RequestBody LocationDTO body) {
        return service.create(body);
    }

    @DeleteMapping("/locations/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @PutMapping("/locations/{name}")
    public LocationDTO updateByName(@PathVariable String name, @RequestBody LocationDTO body) {
        return service.updateByName(name, body);
    }
}
