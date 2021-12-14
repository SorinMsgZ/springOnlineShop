package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDTO> listAll() {
        return orderRepository.findAll().stream()
                .map(OrderDTO::of)
                .collect(Collectors.toList());
    }

    public OrderDTO readById(int id) {
        return orderRepository.findById(id)
                .map(OrderDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public OrderDTO create(OrderDTO input) {
        Order order = input.toEntity();
        return OrderDTO.of(orderRepository.save(order));
    }

    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    public OrderDTO updateById(int id, OrderDTO input) {
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(order);
        orderRepository.save(order);
        return OrderDTO.of(order);
    }

}
