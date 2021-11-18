package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.OrderDetailId;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,OrderDetailId> {
}
