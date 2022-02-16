package ro.msg.learning.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.entities.OrderDetailId;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.repositories.*;


@Controller
public class CartMvcController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderRepository orderRepository;
    private int actualQty;

    @GetMapping("/addOrderDetail/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        OrderDetailId orderDetailId = new OrderDetailId();
        orderDetailId.setOrderId(1);
        orderDetailId.setProductId(id);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setIdOrderProduct(orderDetailId);
        orderDetail.setOrder(orderRepository.getById(1));
        orderDetail.setProduct(product);

        try {
            actualQty = orderDetailRepository.findByIdOrderProduct(orderDetail.getIdOrderProduct()).get().getQuantity();
            orderDetail.setQuantity(actualQty + 1);
            orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            actualQty = 0;
            orderDetail.setQuantity(actualQty + 1);
            orderDetailRepository.save(orderDetail);
        }

        return "redirect:/index";
    }

    @GetMapping("/viewCart")
    public String showCart(Model model) {
        model.addAttribute("orderDetails", orderDetailRepository.findAll());
        return "shop-cart";
    }

    @GetMapping("/submitOrder")
    public String submitOrder() {

        return "shop-submit";
    }
}
