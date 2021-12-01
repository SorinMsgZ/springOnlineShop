package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class OrderCreationService {
    private final OrderRepository orderRepository;
    private final Context context;
    private final StockService stockService;
    private final OrderDetailService orderDetailService;
    private final OrderService orderService;

    List<OrderObject> objectStructureList = new ArrayList<>();

    public void createOrder(OrderCreationDTO input) {

        List<ProdOrdCreationDTO> listProducts = input.getProduct();

        for (ProdOrdCreationDTO product : listProducts) {
            try {
                Location location = context.executeStrategy(product.getProductID(), product.getProductQty());
                OrderObject object =
                        new OrderObject(location, product.toEntity().getProduct(), product.getProductQty());
                objectStructureList.add(object);

                OrderBasket orderBasket = new OrderBasket();
                new ObserverStock(orderBasket, stockService);

                LocalDateTime createdAtDTO = input.getCreatedAt();
                Address addressDTO = input.getDeliveryAddress();
                OrderDTO newOrderDTO = new OrderDTO(location, createdAtDTO, addressDTO);
                orderService.createOrder(newOrderDTO);
                Order theOrder = orderRepository
                        .findAll()
                        .stream()
                        .filter(orders -> (orders.getCreatedAt() == createdAtDTO) &&
                                (orders.getAddress() == addressDTO)).findFirst().orElseThrow();
                new ObserverOrderDetail(orderBasket, theOrder, orderDetailService);

                orderBasket.setOrderObject(object);

            } catch (Exception exception) {

            }
        }
    }

    public boolean availableStockForEachProduct(OrderCreationDTO input) {
        return (objectStructureList.size() == input.getProduct().size());
    }

    public String printObjectWithStock() {
        return objectStructureList.toString();
    }
}

