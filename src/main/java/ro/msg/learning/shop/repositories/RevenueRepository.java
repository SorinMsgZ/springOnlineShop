package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Revenue;

import java.util.Optional;

public interface RevenueRepository extends JpaRepository<Revenue, Location> {
    Optional<Revenue> findByLocation(Location location);
    void deleteByLocation(Location location);
}
