package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MostAbundantStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;

    @Override
    public Location findLocationAndTakeProducts(int productId, int productQty) {
        Product searchProduct = productService.readById(productId).toEntity();
        List<StockDTO> listStocks = stockService.listAll();

        return listStocks
                .stream()
                .filter(stock -> (stock.getProduct().equals(searchProduct)) && (stock.getQuantity() >= productQty))
                .max(Comparator.comparing(StockDTO::getQuantity))
                .orElseThrow(NoSuitableLocationsFound::new)
                .getLocation();
    }
}

