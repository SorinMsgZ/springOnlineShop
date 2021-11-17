package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory,Integer> {
    //List<ProductCategory> findByName(String name);
}
