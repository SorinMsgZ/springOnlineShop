package ro.msg.learning.shop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Address;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderObjectInputDTO {
    private LocalDateTimeDTO createdAt;
    private Address deliveryAddress;
    private List<ProdOrdCreatorDTO> product;

    public OrderObjectInputDTO(LocalDateTimeDTO createdAt, Address deliveryAddress,
                               List<ProdOrdCreatorDTO> product) {
        this.createdAt = createdAt;
        this.deliveryAddress = deliveryAddress;
        this.product = product;
    }
}
