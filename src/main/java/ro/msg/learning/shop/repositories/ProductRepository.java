package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
//List<Product> findByName(String name);
}
