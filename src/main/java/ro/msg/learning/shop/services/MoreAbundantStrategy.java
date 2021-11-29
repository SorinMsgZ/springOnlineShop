package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class MoreAbundantStrategy implements FindLocationStrategy{
    private final StockService stockService;
    private final ProductService productService;

    @Override
    public Location findLocationAndTakeProducts(int productId, int productQty) {
        Product searchProduct = productService.readSingleProduct(productId).toEntity();

        List<StockDTO> listStocks = stockService.listStock();
        Optional<StockDTO> listStocksFound =
                listStocks.stream().filter(stock -> stock.getProduct().equals(searchProduct)).findAny();
        Optional<StockDTO> listStocksFoundMatchQty = listStocksFound.stream()
                .filter(stockQty -> stockQty.getQuantity()>productQty).findAny();
        StockDTO matchStock=listStocksFoundMatchQty.stream().sorted(Comparator.comparing(StockDTO::getQuantity).reversed()).collect(Collectors
                .toList()).get(0);
        return matchStock.getLocation();
    }
}

