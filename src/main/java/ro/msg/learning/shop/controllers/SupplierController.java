package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.services.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;

    @GetMapping("/suppliers")
    public List<SupplierDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/suppliers/{name}")
    public SupplierDTO readByName(@PathVariable String name) {
        return service.readByName(name);
    }

    @PostMapping("/suppliers")
    public SupplierDTO create(@RequestBody SupplierDTO body) {
        return service.create(body);
    }

    @DeleteMapping("/suppliers/{name}")
    public void deleteByName(@PathVariable  String name) {
        service.deleteByName(name);
    }

    @PutMapping("/suppliers/{name}")
    public SupplierDTO updateByName(@PathVariable  String name, @RequestBody SupplierDTO body) {
        return service.updateByName(name, body);
    }
}
