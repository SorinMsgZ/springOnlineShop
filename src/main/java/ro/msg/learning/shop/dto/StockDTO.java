package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.*;

@Data
@Builder
public class StockDTO {
    //    TODO implement without id?!
    //    private StockId idProductLocation;
    private int productId;
    private int locationId;
    private Product product;
    private Location location;
    private int quantity;

    public StockDTO(int productId, int locationId, Product product, Location location, int quantity) {
//        this.idProductLocation = idProductLocation;
        this.productId = productId;
        this.locationId = locationId;
        this.product = product;
        this.location = location;
        this.quantity = quantity;
    }

    public Stock toEntity() {
        Stock result = new Stock();
//        result.setIdProductLocation(idProductLocation);
        StockId stockId = new StockId();
        stockId.setProductId(productId);
        stockId.setLocationId(locationId);
        result.setIdProductLocation(stockId);

        result.setProduct(product);
        result.setLocation(location);
        result.setQuantity(quantity);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Stock stock) {
//        stock.setIdProductLocation(idProductLocation);
        StockId stockId = new StockId();
        stockId.setProductId(productId);
        stockId.setLocationId(locationId);
        stock.setIdProductLocation(stockId);

        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(quantity);
    }

    public static StockDTO of(Stock entity) {
      /*  StockDTO stockDTO =new StockDTO(entity.getIdProductLocation().getProductId(),entity.getIdProductLocation().getLocationId(), entity
                .getProduct(), entity.getLocation(), entity.getQuantity());
      return stockDTO;*/

        return StockDTO.builder()
//                .idProductLocation(entity.getIdProductLocation())
                .productId(entity.getIdProductLocation().getProductId())
                .locationId(entity.getIdProductLocation().getLocationId())

                .product(entity.getProduct())
                .location(entity.getLocation())
                .quantity(entity.getQuantity())
                .build();
    }
}


