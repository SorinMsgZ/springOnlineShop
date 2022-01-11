package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier,String> {
    Optional<Supplier> findByName(String name);
    void deleteByName(String name);
    void deleteAll();
}

