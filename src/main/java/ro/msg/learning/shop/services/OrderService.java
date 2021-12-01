package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.repositories.OrderRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderDTO createOrder(OrderDTO input) {
        Order product = input.toEntity();
        return OrderDTO.of(orderRepository.save(product));
    }
}
