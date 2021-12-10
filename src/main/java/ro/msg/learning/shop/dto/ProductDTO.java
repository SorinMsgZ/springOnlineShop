package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private double weight;
    private int productCategoryId;
    private String productCategoryName;
    private String productCategoryDescription;
    private Supplier supplier;
    private String imageUrl;

    public ProductDTO(int id, String name, String description, BigDecimal price, double weight, int productCategoryId,
                      String productCategoryName, String productCategoryDescription,
                      Supplier supplier, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
        this.productCategoryDescription = productCategoryDescription;
        this.supplier = supplier;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        Product result = new Product();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Product product) {
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setWeight(weight);

        ProductCategory cat = new ProductCategory();
        cat.setId(productCategoryId);
        cat.setName(productCategoryName);
        cat.setDescription(productCategoryDescription);
        product.setCategory(cat);

        product.setSupplier(supplier);
        product.setImageUrl(imageUrl);
    }

    public static ProductDTO of(Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .weight(entity.getWeight())

                .productCategoryId(entity.getCategory().getId())
                .productCategoryName(entity.getCategory().getName())
                .productCategoryDescription(entity.getCategory().getDescription())

                .supplier(entity.getSupplier())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}
