package ro.msg.learning.shop.repositories;


import ro.msg.learning.shop.entities.ProductJdbc;

import java.util.List;
import java.util.Optional;

public interface ProductJdbcRepository {

    ProductJdbc save(ProductJdbc productJdbc);

    Optional<ProductJdbc> findById(int id);

    void remove(ProductJdbc productJdbc);

    List<ProductJdbc> findAll();
}
