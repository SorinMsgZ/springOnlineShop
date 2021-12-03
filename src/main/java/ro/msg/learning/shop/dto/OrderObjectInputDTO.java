package ro.msg.learning.shop.dto;

import lombok.Data;
import ro.msg.learning.shop.entities.Address;
import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderObjectInputDTO {
    private LocalDateTime createdAt;
    private Address deliveryAddress;
    private List<ProdOrdCreatorDTO> product;

//    TODO are the toEntity(), copy() and of() methods needed here??
}
