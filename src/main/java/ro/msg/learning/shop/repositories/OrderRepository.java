package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.Order;

public interface OrderRepository extends CrudRepository<Order,Integer> {
}
