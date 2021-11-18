/*
package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryManagementService {
    private final ProductCategoryRepository repository;

    public List<ProductCategoryDTO> listProductCategories() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(ProductCategoryDTO::of)
                .collect(Collectors.toList());
    }

    public ProductCategoryDTO readSingleProductCategory(int id) {
        return repository.findById(id)
                .map(ProductCategoryDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public ProductCategoryDTO createProductCategory(ProductCategoryDTO input) {
        ProductCategory created = repository.save(input.toEntity());
        return ProductCategoryDTO.of(created);
    }
    public void deleteProductCategory(int id){
        repository.deleteById(id);
    }
    public ProductCategoryDTO updateProductCategory(int id, ProductCategoryDTO input){
        ProductCategory productCategory = repository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(productCategory);
        repository.save(productCategory);
        return ProductCategoryDTO.of(productCategory);
    }
}
*/
