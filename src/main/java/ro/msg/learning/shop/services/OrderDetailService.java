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
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService {
    public final OrderDetailRepository orderDetailRepository;

    public List<OrderDetailDTO> listOrderDetail() {
        return StreamSupport.stream(orderDetailRepository.findAll().spliterator(), false)
                .map(OrderDetailDTO::of)
                .collect(Collectors.toList());
    }

    public OrderDetailDTO readSingleOrderDetail(OrderDetailId id) {
        return orderDetailRepository.findById(id)
                .map(OrderDetailDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public OrderDetailDTO createOrderDetail(OrderDetailDTO input) {
        OrderDetail orderDetail = input.toEntity();
        return OrderDetailDTO.of(orderDetailRepository.save(orderDetail));
    }
}
