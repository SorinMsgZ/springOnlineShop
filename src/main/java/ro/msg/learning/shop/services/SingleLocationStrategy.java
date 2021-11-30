package ro.msg.learning.shop.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.exceptions.StockNotFound;

import java.io.NotActiveException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SingleLocationStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;

    @Override
    public Location findLocationAndTakeProducts(int productId, int productQty) {

        Product searchProduct = productService.readSingleProduct(productId).toEntity();
        List<StockDTO> listStocks = stockService.listStock();

        return listStocks
                .stream()
                .filter(stock -> (stock.getProduct().equals(searchProduct)) && (stock.getQuantity() >= productQty))
                .map(StockDTO::getLocation)
                .min(Comparator.comparing(Location::getId))
                .orElseThrow(NotFoundException::new);
    }
}
