package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.ProductCategory;

@Data
@Builder
@NoArgsConstructor
public class ProductCategoryDTO {

    String name;
    String description;

    public ProductCategoryDTO( String name, String description) {

        this.name = name;
        this.description = description;
    }

    public ProductCategory toEntity() {
        ProductCategory result = new ProductCategory();

        result.setName(name);
        result.setDescription(description);

        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(ProductCategory productCategory) {

        productCategory.setName(name);
        productCategory.setDescription(description);
    }

    public static ProductCategoryDTO of(ProductCategory entity) {
        return ProductCategoryDTO.builder()

                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
