package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;
import ro.msg.learning.shop.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.*;
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

    private final List<OrderObject> objectStructureList = new ArrayList<>();
    private final List<OrderDTO> orderDTOList = new ArrayList<>();

    @Value("${strategy.findLocation}")
    private String locationStrategy;

    public List<OrderDTO> createOrder(OrderObjectInputDTO input) {

        List<ProdOrdCreatorDTO> customerProductList = input.getProduct();

        OrderBasket orderBasket = new OrderBasket();

        Map<Integer, Integer> productLocationMap;
        try {
            productLocationMap = context.executeStrategy(input);
        } catch (NoSuitableLocationsFound exception) {
            throw new NoSuitableLocationsFound();
        }
        for (ProdOrdCreatorDTO product : customerProductList) {
            int prodId = product.getProductID();
            int locId = productLocationMap.get(prodId);
            Location location = stockService.listAll().stream()
                    .filter(stock -> (stock.getProduct().getId() == prodId) && (stock.getLocation().getId() == locId))
                    .map(StockDTO::getLocation).findFirst().orElseThrow(NoSuitableLocationsFound::new);

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
}

