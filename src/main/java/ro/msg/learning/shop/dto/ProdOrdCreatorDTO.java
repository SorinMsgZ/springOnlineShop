package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdOrdCreatorDTO {
    private int productID;
    private int productQty;

    public ProdOrdCreatorDTO(int productID, int productQty) {
        this.productID = productID;
        this.productQty = productQty;
    }
}
