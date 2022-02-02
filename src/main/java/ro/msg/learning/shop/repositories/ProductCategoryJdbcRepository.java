package ro.msg.learning.shop.repositories;


import ro.msg.learning.shop.entities.ProductCategoryJdbc;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryJdbcRepository {

    ProductCategoryJdbc save(ProductCategoryJdbc productCategoryJdbc);

    Optional<ProductCategoryJdbc> findById(int id);

    void remove(ProductCategoryJdbc productCategoryJdbc);

    List<ProductCategoryJdbc> findAll();
}
