package ro.msg.learning.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import ro.msg.learning.shop.entities.Stock;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
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

    public StockExportDTO(Stock stock) {
        this.productId = stock.getIdProductLocation().getProductId();
        this.locationId = stock.getIdProductLocation().getLocationId();
        this.quantity = stock.getQuantity();
    }
}
