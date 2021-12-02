package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDTO> listOrder() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(OrderDTO::of)
                .collect(Collectors.toList());
    }

    public OrderDTO readSingleOrder(int id) {
        return orderRepository.findById(id)
                .map(OrderDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public OrderDTO createOrder(OrderDTO input) {
        Order order = input.toEntity();
        return OrderDTO.of(orderRepository.save(order));
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    public OrderDTO updateOrder(int id, OrderDTO input) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(order);
        orderRepository.save(order);
        return OrderDTO.of(order);
    }

}
