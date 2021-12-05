package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Order;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class OrderDTO {

    private Location shippedFrom;
    private LocalDateTime createdAt;
    private Address address;

    public OrderDTO( Location shippedFrom, LocalDateTime createdAt, Address address) {

        this.shippedFrom = shippedFrom;
        this.createdAt = createdAt;
        this.address = address;
    }

    public Order toEntity() {
        Order result = new Order();

        result.setShippedFrom(shippedFrom);
        result.setCreatedAt(createdAt);
        result.setAddress(address);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Order order) {

        order.setShippedFrom(shippedFrom);
        order.setCreatedAt(createdAt);
        order.setAddress(address);
    }

    public static OrderDTO of(Order entity) {
        return OrderDTO.builder()

                .shippedFrom(entity.getShippedFrom())
                .createdAt(entity.getCreatedAt())
                .address(entity.getAddress())
                .build();
    }
}