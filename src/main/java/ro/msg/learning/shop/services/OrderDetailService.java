package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.OrderDetailId;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.OrderDetailRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService {
    public final OrderDetailRepository orderDetailRepository;

    public List<OrderDetailDTO> listAll() {
        return orderDetailRepository.findAll().stream()
                .map(OrderDetailDTO::of)
                .collect(Collectors.toList());
    }

    public OrderDetailDTO readById(OrderDetailId id) {
        return orderDetailRepository.findById(id)
                .map(OrderDetailDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public OrderDetailDTO create(OrderDetailDTO input) {
        OrderDetail orderDetail = input.toEntity();
        return OrderDetailDTO.of(orderDetailRepository.save(orderDetail));
    }

    public void deleteById(OrderDetailId id) {
        orderDetailRepository.deleteById(id);
    }

    public OrderDetailDTO updateById(OrderDetailId id, OrderDetailDTO input) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(orderDetail);
        orderDetailRepository.save(orderDetail);
        return OrderDetailDTO.of(orderDetail);

    }

}
