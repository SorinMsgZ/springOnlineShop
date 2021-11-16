package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier,Integer> {
    List<Supplier> findByName(String name);
}
