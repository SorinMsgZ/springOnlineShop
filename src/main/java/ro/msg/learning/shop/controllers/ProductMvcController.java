package ro.msg.learning.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ro.msg.learning.shop.entities.OrderDetail;
import ro.msg.learning.shop.repositories.OrderDetailRepository;
import ro.msg.learning.shop.repositories.ProductRepository;

@Controller
public class ProductMvcController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("products", productRepository.findAll());
        long toCartAddedItemsNb = orderDetailRepository.findAll().stream()
                .mapToInt(OrderDetail::getQuantity).sum();
        model.addAttribute("itemNumber", toCartAddedItemsNb);

        return "shop-products";
    }
}
