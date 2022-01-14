package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMissingStockDTO {
    private String type;
    private String message;

    public static ErrorMissingStockDTO of(NoSuitableLocationsFound exception) {
        return new ErrorMissingStockDTO("no_stock_found", exception.getMessage());
    }
}
