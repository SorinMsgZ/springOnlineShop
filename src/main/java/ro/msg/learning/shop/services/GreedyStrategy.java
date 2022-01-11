package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;
import ro.msg.learning.shop.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GreedyStrategy implements FindLocationStrategy {
    private final StockService stockService;
    private final ProductService productService;

    private final HashMap<Integer, Integer> productLocation = new HashMap<>();
    private final List<String> cityStateDestinationStockOriginList = new ArrayList<>();
    private boolean productIsAvailable;
    private List<Integer> customerProductIdList;
    private final Map<Integer, Integer> customerProductIdQuantityMap = new HashMap<>();
    private List<StockDTO> listStocks;

    @Override
    public HashMap<Integer, Integer> findLocationAndTakeProducts(OrderObjectInputDTO input) {

        listStocks = stockService.listAll();

        Address deliveryAddress = input.getDeliveryAddress();
        List<Location> distinctStockDTOLocationList = getAvailableDistinctStockLocationList();
        buildLocationListParameterForMapQuest(deliveryAddress, distinctStockDTOLocationList);

        Location deliveryLocation = new Location();
        List<Location> customerAndStockLocationList = new ArrayList<>();
        customerAndStockLocationList.add(deliveryLocation);
        customerAndStockLocationList.addAll(distinctStockDTOLocationList);

        ProximityResponseDTO proximityResponseDTO =
                ProximityResponseDTO.returnProximityResponse(cityStateDestinationStockOriginList);

        List<Integer> distanceFromAllStockOriginToDestinationList = proximityResponseDTO.getDistance();

        Map<Integer, Integer> distanceCityStateFromStockOriginToDestinationMap = new HashMap<>();
        for (int iterator = 1; iterator < distanceFromAllStockOriginToDestinationList.size(); iterator++) {
            distanceCityStateFromStockOriginToDestinationMap
                    .put(distanceFromAllStockOriginToDestinationList.get(iterator), customerAndStockLocationList
                            .get(iterator).getId());
        }

        Collections.sort(distanceFromAllStockOriginToDestinationList);

        customerProductIdList =
                input.getProduct().stream().map(ProdOrdCreatorDTO::getProductID).collect(Collectors.toList());
        List<Integer> orderedQuantityList =
                input.getProduct().stream().map(ProdOrdCreatorDTO::getProductQty).collect(Collectors.toList());
        for (int iterator = 0; iterator < customerProductIdList.size(); iterator++
        ) {
            customerProductIdQuantityMap.put(customerProductIdList.get(iterator), orderedQuantityList.get(iterator));
        }

        getProductFromNearestStock(distanceFromAllStockOriginToDestinationList, distanceCityStateFromStockOriginToDestinationMap);

        return productLocation;
    }

    public List<Location> getAvailableDistinctStockLocationList() {

        return listStocks
                .stream()
                .map(StockDTO::getLocation)
                .distinct()
                .collect(Collectors.toList());
    }

    public void buildLocationListParameterForMapQuest(Address toCityStateDestination,

                                                      List<Location> fromCityStateStockOriginList) {

        ProximityLocationDTO formatDestination = ProximityLocationDTO.of(toCityStateDestination);
        cityStateDestinationStockOriginList.add(formatDestination.getCityState());
        fromCityStateStockOriginList
                .forEach(location -> cityStateDestinationStockOriginList
                        .add(ProximityLocationDTO.of(location.getAddress()).getCityState()));
    }

    public Integer getQuantityFilteringStockByLocationIdAndProductId(int productId, int locationId) {
        return listStocks
                .stream()
                .filter(stock -> (stock.getLocation().getId() == locationId) &&
                        (stock.getProduct().getId() == productId))
                .map(StockDTO::getQuantity).findAny().orElseThrow(NoSuitableLocationsFound::new);
    }

    public void getProductFromNearestStock(
            List<Integer> distanceProximityList,
            Map<Integer, Integer> distanceLocationProximityMap) {

        final int indexOfDeliveryCustomerLocation = 0;
        distanceProximityList.remove(indexOfDeliveryCustomerLocation);

        for (Integer customerProductId : customerProductIdList) {

            for (Integer distanceIterator : distanceProximityList) {
                productIsAvailable = true;
                Integer availableLocationId = distanceLocationProximityMap.get(distanceIterator);
                try {
                    int availableQuantityFilteredByProductAndLocation =
                            getQuantityFilteringStockByLocationIdAndProductId(customerProductId, availableLocationId);
                    if (availableQuantityFilteredByProductAndLocation >=
                            customerProductIdQuantityMap.get(customerProductId)) {
                        productIsAvailable = true;
                        productLocation.put(customerProductId, availableLocationId);
                        break;
                    } else {
                        productIsAvailable = false;
                    }

                } catch (NotFoundException e) {
                    productIsAvailable = false;
                }
            }
            if (!productIsAvailable) {
                throw new NoSuitableLocationsFound();
            }
        }
    }
}

