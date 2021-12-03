package ro.msg.learning.shop.controllers;

import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.dto.OrderObjectInputDTO;
import ro.msg.learning.shop.services.OrderCreatorService;
import ro.msg.learning.shop.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api")

public class OrderCreatorController {

    private final OrderService orderService;
    private final OrderCreatorService orderCreatorService;

    public OrderCreatorController(OrderService orderService,
                                  OrderCreatorService orderCreatorService) {
        this.orderService = orderService;
        this.orderCreatorService = orderCreatorService;
    }

    @GetMapping("/orders")
    public List<OrderDTO> listAllOrders() {
        return orderService.listOrder();
    }

    @GetMapping("/orders/{id}")
    public OrderDTO readSingleOrder(@PathVariable int id) {
        return orderService.readSingleOrder(id);
    }

    @PostMapping("/orders")
    public List<OrderDTO> createOrder(@RequestBody OrderObjectInputDTO body) {
        return orderCreatorService.createOrder(body);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteSingleOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/orders/{id}")
    public OrderDTO updateSingleOrder(@PathVariable int id, @RequestBody OrderDTO body) {
        return orderService.updateOrder(id, body);
    }

}
