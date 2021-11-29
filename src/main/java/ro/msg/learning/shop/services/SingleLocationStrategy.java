package ro.msg.learning.shop.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Stock;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor

public class SingleLocationStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;

    @Override
    public Location findLocationAndTakeProducts(int productId, int productQty) {

        Product searchProduct = productService.readSingleProduct(productId).toEntity();

        List<StockDTO> listStocks = stockService.listStock();
        Optional<StockDTO> listStocksFound =
                listStocks.stream().filter(stock -> stock.getProduct().equals(searchProduct)).findAny();
        Optional<StockDTO> listStocksFoundMatchQty = listStocksFound.stream()
                .filter(stockQty -> stockQty.getQuantity()>productQty).findFirst();
        return listStocksFoundMatchQty.get().getLocation();
    }
}
