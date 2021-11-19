package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.ProductCategory;

@Data
@Builder
public class ProductCategoryDTO {
    int id;
    String name;

    public ProductCategory toEntity() {
        ProductCategory result = new ProductCategory();
        result.setName(name);
        result.setId(id);

        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(ProductCategory productCategory) {
        productCategory.setName(name);
        productCategory.setId(id);
    }

    public static ProductCategoryDTO of(ProductCategory entity) {
        return ProductCategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }


}
