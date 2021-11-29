package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")

public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public List<ProductDTO> listAllProducts() {
        return service.listProduct();
    }

    @GetMapping("/products/{id}")
    public ProductDTO readSingleProduct(@PathVariable int id) {
        return service.readSingleProduct(id);
    }

    @PostMapping("/products")
    public ProductDTO createProduct(@RequestBody ProductDTO body) {
        return service.createProduct(body);
    }

    @DeleteMapping("/products/{id}")
    public void deleteSingleProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }

    @PutMapping("/products/{id}")
    public ProductDTO updateSingleProduct(@PathVariable int id, @RequestBody ProductDTO body) {
        return service.updateProduct(id, body);
    }

}
