package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
