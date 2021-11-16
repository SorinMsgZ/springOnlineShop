package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.OrderDetailId;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,OrderDetailId> {
}
