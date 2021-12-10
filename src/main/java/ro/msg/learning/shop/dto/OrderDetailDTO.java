package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.*;

@Data
@Builder
public class OrderDetailDTO {

    private Order order;
    private Product product;
    private int quantity;

    public OrderDetailDTO(Order order, Product product, int quantity) {

        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderDetail toEntity() {
        OrderDetail result = new OrderDetail();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(OrderDetail orderDetail) {

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
    }

    public static OrderDetailDTO of(OrderDetail entity) {
        return OrderDetailDTO.builder()

                .order(entity.getOrder())
                .product(entity.getProduct())
                .quantity(entity.getQuantity())
                .build();
    }
}
