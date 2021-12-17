package ro.msg.learning.shop.controllers;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Profile("test")
public class ClearPopulateDbController {

    private final ProductService service;

    public ClearPopulateDbController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public List<ProductDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/products/{id}")
    public ProductDTO readById(@PathVariable int id) {
        return service.readById(id);
    }

    @PostMapping("/products")
    public ProductDTO create(@RequestBody ProductDTO body) {
        return service.create(body);
    }

    @DeleteMapping("/products/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

    @PutMapping("/products/{id}")
    public ProductDTO updateById(@PathVariable int id, @RequestBody ProductDTO body) {
        return service.updateById(id, body);
    }


}
