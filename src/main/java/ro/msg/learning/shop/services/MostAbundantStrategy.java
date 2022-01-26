package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderObjectInputDTO;
import ro.msg.learning.shop.dto.ProdOrdCreatorDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MostAbundantStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;
    private final HashMap<Integer, Integer> productLocation = new HashMap<>();

    @Override
    public HashMap<Integer, Integer> findLocationAndTakeProducts(OrderObjectInputDTO input) {
        List<Integer> orderedProductIdList =
                input.getProduct().stream().map(ProdOrdCreatorDTO::getProductID).collect(Collectors.toList());
        List<ProdOrdCreatorDTO> prodOrdCreatorDTOList = input.getProduct();

        for (Integer prodId : orderedProductIdList) {
            int orderedQty = prodOrdCreatorDTOList
                    .stream()
                    .filter(prodOrd -> prodOrd.getProductID() == prodId)
                    .map(ProdOrdCreatorDTO::getProductQty)
                    .findFirst().orElseThrow();

            Product searchProduct = productService.readById(prodId).toEntity();
            List<StockDTO> listStocks = stockService.listAll();

            Location locationFound = listStocks
                    .stream()
                    .filter(stock -> (stock.getProduct().equals(searchProduct)) && (stock.getQuantity() >= orderedQty))
                    .max(Comparator.comparing(StockDTO::getQuantity))
                    .orElseThrow(NoSuitableLocationsFound::new)
                    .getLocation();
            productLocation.put(prodId, locationFound.getId());
        }

        return productLocation;
    }
}

