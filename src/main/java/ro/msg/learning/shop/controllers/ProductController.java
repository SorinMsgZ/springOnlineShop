package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/products")
    public List<ProductDTO> listProducts() {
        return service.listProduct();
    }

    @GetMapping("/products/{id}")
    public ProductDTO readSingle(@PathVariable int id) {
        return service.readSingleProduct(id);
    }

    @PostMapping("/products")
    public ProductDTO create(@RequestBody ProductDTO body) {
        return service.createProduct(body);
    }

    @DeleteMapping("/products/{id}")
    public void deleteSingle(@PathVariable int id) {
        service.deleteProduct(id);
    }

    @PutMapping("/products/{id}")
    public ProductDTO updateSingle(@PathVariable int id, @RequestBody ProductDTO body) {
        return service.updateProduct(id, body);
    }

}
