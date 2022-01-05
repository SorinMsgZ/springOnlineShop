package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProximityStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;

    @Override
    public Location findLocationAndTakeProducts(int productId, int productQty) {
        Product searchProduct = productService.readById(productId).toEntity();
        List<StockDTO> listStocks = stockService.listAll();



        return null;
    }
}
