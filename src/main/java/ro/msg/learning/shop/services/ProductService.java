package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public List<ProductDTO> listProduct() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(ProductDTO::of)
                .collect(Collectors.toList());
    }

    public ProductDTO readSingleProduct(int id) {
        return repository.findById(id)
                .map(ProductDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public ProductDTO createProduct(ProductDTO input) {
        Product created = repository.save(input.toEntity());
        return ProductDTO.of(created);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public ProductDTO updateProduct(int id, ProductDTO input) {
        Product product = repository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(product);
        repository.save(product);
        return ProductDTO.of(product);
    }
}
