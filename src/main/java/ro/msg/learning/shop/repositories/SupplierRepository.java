package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Supplier;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
    List<Supplier> findByName(String name);
}

