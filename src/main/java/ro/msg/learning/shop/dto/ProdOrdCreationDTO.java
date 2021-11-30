package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.Product;

@Data
@Builder
public class ProdOrdCreationDTO {
    private int productID;
    private int productQty;

    public ProdOrdCreationDTO(int productID, int productQty) {
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

    //  (product ID and quantity) contained in the order.
   /* public static ProdOrdCreationDTO of (OrderDetail entity) {
        ProdOrdCreationDTO result = new ProdOrdCreationDTO();
        result.setProductID(entity.getProduct().getId());
        result.setProductQty(entity.getQuantity());
       return result;
    }*/
}
