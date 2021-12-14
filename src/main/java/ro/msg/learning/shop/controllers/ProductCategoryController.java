package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.services.ProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService service;

    @GetMapping("/productcategories")
    public List<ProductCategoryDTO> listAll() {
        return service.listAll();
    }

    @GetMapping("/productcategories/{name}")
    public ProductCategoryDTO readByName(@PathVariable String name) {
        return service.readByName(name);
    }

    @PostMapping("/productcategories")
    public ProductCategoryDTO create(@RequestBody ProductCategoryDTO body) {
        return service.create(body);
    }

    @DeleteMapping("/productcategories/{name}")
    public void deleteByName(@PathVariable String name) {
        service.deleteByName(name);
    }

    @PutMapping("/productcategories/{name}")
    public ProductCategoryDTO updateByName(@PathVariable String name, @RequestBody ProductCategoryDTO body) {
        return service.updateByName(name, body);
    }
}
