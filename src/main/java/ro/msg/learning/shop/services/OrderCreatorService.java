package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;
import ro.msg.learning.shop.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class OrderCreatorService {
    private final OrderRepository orderRepository;
    private final Context context;
    private final StockService stockService;
    private final OrderDetailService orderDetailService;
    private final OrderService orderService;
    private final ProductService productService;

    List<OrderObject> objectStructureList = new ArrayList<>();
    List<OrderDTO> orderDTOList = new ArrayList<>();

    Location location;
    @Value("${strategy.findLocation}")
    String locationStrategy;

    public List<OrderDTO> createOrder(OrderObjectInputDTO input) {

        List<ProdOrdCreatorDTO> listProducts = input.getProduct();

        OrderBasket orderBasket = new OrderBasket();

        if (locationStrategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            Address deliveryAddress = input.getDeliveryAddress();
            List<StockDTO> listStocks=stockService.listAll();
            List<Location> listStocksLocation=listStocks.stream().map(stock->stock.getLocation()).collect(Collectors.toList());
            List<Address> listStockLocationAddress=listStocksLocation.stream().map(location->location.getAddress()).collect(Collectors.toList());
            returnProximityResponse();
        }

        for (ProdOrdCreatorDTO product : listProducts) {
            try {
                location = context.executeStrategy(product.getProductID(), product.getProductQty());

            } catch (NoSuitableLocationsFound exception) {
                throw new NoSuitableLocationsFound();
            }

            Product allAttributeProduct = productService.readById(product.getProductID()).toEntity();

            OrderObject object =
                    new OrderObject(location, allAttributeProduct, product.getProductQty());
            objectStructureList.add(object);

            new ObserverStock(orderBasket, stockService);

            LocalDateTime createdAtDTO = input.getCreatedAt();
            Address addressDTO = input.getDeliveryAddress();

            OrderDTO newOrderDTO = new OrderDTO();
            newOrderDTO.setShippedFrom(location);
            newOrderDTO.setCreatedAt(createdAtDTO);
            newOrderDTO.setAddress(addressDTO);

            orderService.create(newOrderDTO);
            orderDTOList.add(newOrderDTO);

            Order theOrder = orderRepository
                    .findAll().stream().filter(orders -> (orders.getCreatedAt().equals(createdAtDTO)) &&
                            (orders.getAddress().getId() == addressDTO.getId())).collect(Collectors.toList())
                    .get(0);

            new ObserverOrderDetail(orderBasket, theOrder, orderDetailService);

            orderBasket.setOrderObject(object);

        }
        return orderDTOList;
    }

    @Bean
    public ProximityResponseDTO returnProximityResponse(List<Location> listLocationsToBeCompared) {
        //POST REST API with RestTemplate & Data Form

        return response
    }

}

