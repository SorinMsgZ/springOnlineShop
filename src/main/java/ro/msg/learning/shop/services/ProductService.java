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

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDTO> listAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::of)
                .collect(Collectors.toList());
    }

    public ProductDTO readById(int id) {
        return productRepository.findById(id)
                .map(ProductDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public ProductDTO create(ProductDTO input) {
        Product product = input.toEntity();
        return ProductDTO.of(productRepository.save(product));
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public ProductDTO updateById(int id, ProductDTO input) {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(product);
        productRepository.save(product);
        return ProductDTO.of(product);
    }
}
