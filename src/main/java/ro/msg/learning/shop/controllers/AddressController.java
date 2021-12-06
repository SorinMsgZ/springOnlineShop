package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.AddressDTO;
import ro.msg.learning.shop.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @GetMapping("/addresses")
    public List<AddressDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/addresses/{streetAddress}")
    public AddressDTO readByStreetAddress(@PathVariable String streetAddress) {
        return service.readByStreetAddress(streetAddress);
    }

    @PostMapping("/addresses")
    public AddressDTO create(@RequestBody AddressDTO body) {
        return service.create(body);
    }

    @DeleteMapping("/addresses/{streetAddress}")
    public void deleteByStreetAddress(@PathVariable String streetAddress) {
        service.deleteByStreetAddress(streetAddress);
    }

    @PutMapping("/addresses/{streetAddress}")
    public AddressDTO updateByStreetAddress(@PathVariable String streetAddress, @RequestBody AddressDTO body) {
        return service.updateByStreetAddress(streetAddress, body);
    }
}
