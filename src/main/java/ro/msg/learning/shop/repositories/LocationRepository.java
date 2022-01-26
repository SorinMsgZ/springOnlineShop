package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findByName(String name);
    Optional<Location> findByAddress(Address address);
    void deleteByName(String name);
}
