package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Revenue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    Optional<Revenue> findByLocation(Location location);
    List<Revenue> findByLocalDate(LocalDate localDate);
    void deleteByLocation(Location location);
}
