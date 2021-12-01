package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dto.OrderDetailDTO;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.*;

public class ObserverOrderDetail extends ObserverObject {

    OrderDetailService orderDetailService;
    Order order;

    public ObserverOrderDetail(OrderBasket subject, Order order, OrderDetailService orderDetailService) {
        super(subject);
        this.subject.attach(this);
        this.order = order;
        this.orderDetailService = orderDetailService;
    }

    @Override
    public void update() {
        createOrderDetail();
    }

    public void createOrderDetail() {

        Product product = subject.getOrderObject().prod;
        int qty = subject.getOrderObject().qty;

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(order, product, qty);
        orderDetailService.createOrderDetail(orderDetailDTO);
    }
}
