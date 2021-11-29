package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class OrderCreationDTO {
    private LocalDateTime createdAt;
    private Address deliveryAddress;
    private List<ProdOrdCreationDTO> product;


    public Order toEntity() {
        Order result = new Order();
        result.setCreatedAt(createdAt);
        result.setAddress(deliveryAddress);

        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Order order) {
        order.setCreatedAt(createdAt);
        order.setAddress(deliveryAddress);

    }

    // every OrderDetail will have the same delivery address & time of creation of the Order
    public static OrderCreationDTO of(Order entity) {
        OrderCreationDTO result = new OrderCreationDTO();

        result.setCreatedAt(entity.getCreatedAt());
        result.setDeliveryAddress(entity.getAddress());
        return result;
    }
}
