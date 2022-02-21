package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Customer;
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
    private Customer customer;

    public OrderDTO(Location shippedFrom, LocalDateTime createdAt, Address address, Customer customer) {

        this.shippedFrom = shippedFrom;
        this.createdAt = createdAt;
        this.address = address;
        this.customer = customer;
    }

    public Order toEntity() {
        Order result = new Order();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Order order) {

        order.setShippedFrom(shippedFrom);
        order.setCreatedAt(createdAt);
        order.setAddress(address);
        order.setCustomer(customer);
    }

    public static OrderDTO of(Order entity) {
        return OrderDTO.builder()

                .shippedFrom(entity.getShippedFrom())
                .createdAt(entity.getCreatedAt())
                .address(entity.getAddress())
                .customer(entity.getCustomer())
                .build();
    }
}