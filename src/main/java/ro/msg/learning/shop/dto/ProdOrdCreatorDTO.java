package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.Product;

@Data
@Builder
public class ProdOrdCreatorDTO {
    private int productID;
    private int productQty;

    public ProdOrdCreatorDTO(int productID, int productQty) {
        this.productID = productID;
        this.productQty = productQty;
    }

    public OrderDetail toEntity() {
        OrderDetail result = new OrderDetail();
        Product product = new Product();
        result.setProduct(product);
        product.setId(productID);

        result.setQuantity(productQty);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(OrderDetail orderDetail) {
        Product product = new Product();
        orderDetail.setProduct(product);
        product.setId(productID);
        orderDetail.setQuantity(productQty);
    }
//    TODO is the of() method needed here??
}
