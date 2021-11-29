package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.entities.Product;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);


}
