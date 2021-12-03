package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.ProductCategory;

@Data
@Builder
public class ProductCategoryDTO {
    int id;
    String name;
    String description;

    public ProductCategoryDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ProductCategory toEntity() {
        ProductCategory result = new ProductCategory();
        result.setId(id);
        result.setName(name);
        result.setDescription(description);

        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(ProductCategory productCategory) {
        productCategory.setId(id);
        productCategory.setName(name);
        productCategory.setDescription(description);
    }

    public static ProductCategoryDTO of(ProductCategory entity) {
        return ProductCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
