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
    private String description;
    private BigDecimal price;
    private double weight;
    private int productCategoryId;
    private String productCategoryName;
    private String productCategoryDescription;
    private int supplierId;
    private String supplierName;
    private String imageUrl;

    public ProductDTO(int id, String name, String description, BigDecimal price, double weight, int productCategoryId,
                      String productCategoryName, String productCategoryDescription, int supplierId,
                      String supplierName, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
        this.productCategoryDescription = productCategoryDescription;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.imageUrl = imageUrl;
    }

    public Product toEntity() {
        Product result = new Product();
        result.setId(id);
        result.setName(name);
        result.setDescription(description);
        result.setPrice(price);
        result.setWeight(weight);
//        result.setCategory(category);
        ProductCategory cat = new ProductCategory();
        cat.setId(productCategoryId);
        cat.setName(productCategoryName);
        cat.setDescription(productCategoryDescription);

        result.setCategory(cat);

//        result.setSupplier(supplier);
        Supplier sup = new Supplier();
        sup.setId(supplierId);
        sup.setName(supplierName);

        result.setSupplier(sup);

        result.setImageUrl(imageUrl);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Product product) {
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setWeight(weight);
//        product.setCategory(category);
        ProductCategory cat = new ProductCategory();
        cat.setId(productCategoryId);
        cat.setName(productCategoryName);
        cat.setDescription(productCategoryDescription);

        product.setCategory(cat);
//        product.setSupplier(supplier);

        Supplier sup = new Supplier();
        sup.setId(supplierId);
        sup.setName(supplierName);

        product.setSupplier(sup);

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

                .supplierId(entity.getSupplier().getId())
                .supplierName(entity.getSupplier().getName())

                .imageUrl(entity.getImageUrl())
                .build();
    }


}
