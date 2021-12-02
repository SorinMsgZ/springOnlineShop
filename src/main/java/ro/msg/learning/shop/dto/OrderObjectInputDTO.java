package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Order;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderObjectInputDTO {
    private LocalDateTime createdAt;
    private Address deliveryAddress;
    private List<ProdOrdCreatorDTO> product;


/*    public Order toEntity() {
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

    public static OrderCreatorDTO of(Order entity) {
        OrderCreatorDTO result = new OrderCreatorDTO();

        result.setCreatedAt(entity.getCreatedAt());
        result.setDeliveryAddress(entity.getAddress());
        return result;
    }*/
}
