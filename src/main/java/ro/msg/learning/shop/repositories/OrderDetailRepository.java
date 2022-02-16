package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.OrderDetailId;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    Optional<OrderDetail> findByIdOrderProduct(OrderDetailId orderDetailId);
}
