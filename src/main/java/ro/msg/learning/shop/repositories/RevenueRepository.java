package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import ro.msg.learning.shop.entities.Revenue;

public interface RevenueRepository extends CrudRepository<Revenue,Integer> {
}
