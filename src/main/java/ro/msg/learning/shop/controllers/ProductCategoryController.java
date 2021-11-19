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
    public List<ProductCategoryDTO> listProductCategories() {
        return service.listProductCategories();
    }

    @GetMapping("/productcategories/{id}")
    public ProductCategoryDTO readSingle(@PathVariable int id) {
        return service.readSingleProductCategory(id);
    }

    @PostMapping("/productcategories")
    public ProductCategoryDTO create(@RequestBody ProductCategoryDTO body) {
        return service.createProductCategory(body);
    }

    @DeleteMapping("/productcategories/{id}")
    public void deleteSingle(@PathVariable int id) {
        service.deleteProductCategory(id);
    }

    @PutMapping("/productcategories/{id}")
    public ProductCategoryDTO updateSingle(@PathVariable int id, @RequestBody ProductCategoryDTO body) {
        return service.updateProductCategory(id, body);
    }
}
