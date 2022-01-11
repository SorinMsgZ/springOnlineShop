package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderObjectInputDTO;
import ro.msg.learning.shop.dto.ProdOrdCreatorDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SingleLocationStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;

    private final HashMap<Integer, Integer> productLocation = new HashMap<>();
    private List<ProdOrdCreatorDTO> prodOrdCreatorDTOList;

    @Override
    public HashMap<Integer, Integer> findLocationAndTakeProducts(OrderObjectInputDTO input) {

        prodOrdCreatorDTOList = input.getProduct();
        List<Integer> orderedProductIdList =
                prodOrdCreatorDTOList.stream().map(ProdOrdCreatorDTO::getProductID).collect(Collectors.toList());
        List<StockDTO> listStocks = stockService.listAll();

        List<Integer> listAvailableLocationId = listStocks
                .stream()
                .map(StockDTO::getLocation)
                .collect(Collectors.toList())
                .stream()
                .distinct()
                .map(Location::getId)
                .collect(Collectors.toList());

        Collections.sort(listAvailableLocationId);

        boolean enoughStockForAllTheOrderedProduct = false;

        for (int availableLocationId : listAvailableLocationId) {
            List<StockDTO> listStockFilteredByLocation =
                    listStocks.stream().filter(stock -> stock.getLocation().getId() == availableLocationId)
                            .collect(Collectors.toList());
            List<Integer> listAvailableProductIdForTheLocation =
                    listStockFilteredByLocation.stream().map(stock -> stock.getProduct().getId())
                            .collect(Collectors.toList());

            if (listAvailableProductIdForTheLocation.containsAll(orderedProductIdList)) {
                enoughStockForAllTheOrderedProduct =
                        checkCustomerOrderedQuantityWithStock(orderedProductIdList, listStockFilteredByLocation, availableLocationId);
                if (enoughStockForAllTheOrderedProduct) {
                    for (int productId : orderedProductIdList) {
                        productLocation.put(productId, availableLocationId);
                    }
                    break;
                }
            } else {
                enoughStockForAllTheOrderedProduct = false;
            }
        }

        if (!enoughStockForAllTheOrderedProduct) throw new NoSuitableLocationsFound();
        return productLocation;
    }

    public boolean checkCustomerOrderedQuantityWithStock(List<Integer> orderedProductIdList,
                                                         List<StockDTO> listStockFilteredByLocation,
                                                         int availableLocationId) {
        boolean stockIsEnough = true;

        for (int orderedProductId : orderedProductIdList) {
            int stockQty = listStockFilteredByLocation
                    .stream()
                    .filter(stock -> (stock.getProduct().getId() == orderedProductId) &&
                            (stock.getLocation().getId() == availableLocationId))
                    .map(StockDTO::getQuantity).collect(Collectors.toList()).get(0);
            int orderedQty = prodOrdCreatorDTOList
                    .stream()
                    .filter(prodOrd -> prodOrd.getProductID() == orderedProductId)
                    .map(ProdOrdCreatorDTO::getProductQty)
                    .findFirst().orElseThrow(NoSuitableLocationsFound::new);

            if (stockQty < orderedQty) {
                stockIsEnough = false;
                break;
            }
        }
        return stockIsEnough;
    }
}

