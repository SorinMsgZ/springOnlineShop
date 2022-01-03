package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.entities.StockId;

import java.io.Serializable;


@Data
@Builder

@AllArgsConstructor
@JsonPropertyOrder({"productId", "locationId", "quantity"})
@Getter
@ToString
@EqualsAndHashCode
public class StockExportDTO implements Serializable {
    @JsonProperty("product")
    private int productId;
    @JsonProperty("location")
    private int locationId;
    @JsonProperty("quantity")
    private int quantity;

    public StockExportDTO() {
    }

    public StockExportDTO(Stock stock) {
        this.productId = stock.getIdProductLocation().getProductId();
        this.locationId = stock.getIdProductLocation().getLocationId();
        this.quantity = stock.getQuantity();
    }

    public Stock toEntity() {
        Stock result = new Stock();
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Stock stock) {
        StockId stockId = new StockId(productId, locationId);
        Product product = new Product();
        product.setId(productId);
        Location location = new Location();
        location.setId(locationId);

        stock.setIdProductLocation(stockId);
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(quantity);
    }

    public static StockExportDTO of(Stock entity) {
        return StockExportDTO.builder()

                .productId(entity.getProduct().getId())
                .locationId(entity.getLocation().getId())
                .quantity(entity.getQuantity())
                .build();
    }
}
