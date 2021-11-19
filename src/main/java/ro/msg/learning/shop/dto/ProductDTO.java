package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDTO {
    private int id;
    private String name;
    private ProductCategory category;
    private Supplier supplier;

    public Product toEntity() {
        Product result = new Product();
        result.setName(name);
        result.setId(id);
        result.setCategory(category);
        result.getSupplier();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Product product) {
        product.setName(name);
        product.setId(id);
        product.setCategory(category);
        product.setSupplier(supplier);
    }

    public static ProductDTO of(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .supplier(entity.getSupplier())
                .build();
    }
}
