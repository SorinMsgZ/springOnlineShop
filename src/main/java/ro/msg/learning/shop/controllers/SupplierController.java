package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.services.ProductService;
import ro.msg.learning.shop.services.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;

    @GetMapping("/suppliers")
    public List<SupplierDTO> listSuppliers() {
        return service.listSupplier();
    }

    @GetMapping("/suppliers/{id}")
    public SupplierDTO readSingle(@PathVariable int id) {
        return service.readSingleSupplier(id);
    }

    @PostMapping("/suppliers")
    public SupplierDTO create(@RequestBody SupplierDTO body) {
        return service.createSupplier(body);
    }

    @DeleteMapping("/suppliers/{id}")
    public void deleteSingle(@PathVariable int id) {
        service.deleteSupplier(id);
    }

    @PutMapping("/suppliers/{id}")
    public SupplierDTO updateSingle(@PathVariable int id, @RequestBody SupplierDTO body) {
        return service.updateSupplier(id, body);
    }
}
