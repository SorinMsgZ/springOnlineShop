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

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository repository;

    public List<ProductCategoryDTO> listAll() {
        return repository.findAll().stream()
                .map(ProductCategoryDTO::of)
                .collect(Collectors.toList());
    }

    public ProductCategoryDTO readByName(String name) {
        return repository.findByName(name)
                .map(ProductCategoryDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public ProductCategoryDTO create(ProductCategoryDTO input) {
        ProductCategory created = repository.save(input.toEntity());
        return ProductCategoryDTO.of(created);
    }

    public void deleteByName(String name) {
        repository.deleteByName(name);
    }

    public ProductCategoryDTO updateByName(String name, ProductCategoryDTO input) {
        ProductCategory productCategory = repository.findByName(name).orElseThrow(NotFoundException::new);
        input.copyToEntity(productCategory);
        repository.save(productCategory);
        return ProductCategoryDTO.of(productCategory);
    }

}
