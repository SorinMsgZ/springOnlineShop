package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
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

    public List<OrderDTO> createOrder(OrderObjectInputDTO input) {

        List<ProdOrdCreatorDTO> listProducts = input.getProduct();

        OrderBasket orderBasket = new OrderBasket();

        for (ProdOrdCreatorDTO product : listProducts) {
            try {
                Location location = context.executeStrategy(product.getProductID(), product.getProductQty());

                Product allAttributeProduct = productService.readSingleProduct(product.getProductID()).toEntity();

                OrderObject object =
                        new OrderObject(location, allAttributeProduct, product.getProductQty());
                objectStructureList.add(object);

                new ObserverStock(orderBasket, stockService);

                LocalDateTime createdAtDTO = input.getCreatedAt();
                Address addressDTO = input.getDeliveryAddress();
                OrderDTO newOrderDTO = new OrderDTO(location, createdAtDTO, addressDTO);
                orderService.createOrder(newOrderDTO);
                orderDTOList.add(newOrderDTO);
//TODO Why no orderID number has been automatically generated?
                Order theOrder = orderRepository
                        .findAll().stream().filter(orders -> (orders.getCreatedAt().equals(createdAtDTO)) &&
                                (orders.getAddress().getId() == addressDTO.getId())).collect(Collectors.toList())
                        .get(0);

                new ObserverOrderDetail(orderBasket, theOrder, orderDetailService);

                orderBasket.setOrderObject(object);
//TODO "ignored" instead of "catch"?; should any exception message be printed?
            } catch (Exception exception) {
                exception.getMessage();
            }
        }
        return orderDTOList;
    }
}

