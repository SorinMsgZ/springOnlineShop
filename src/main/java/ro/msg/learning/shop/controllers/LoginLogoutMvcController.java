package ro.msg.learning.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.User;

import ro.msg.learning.shop.repositories.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class LoginLogoutMvcController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/shop")
    public String showWelcomePage() {

        return "shop-welcome";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "shop-login";
    }

    @PostMapping("/loginUser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "shop-login";
        }

        String inputUserPassword = user.getPassword();

        String dbPassword = userRepository.findByUsername(user.getUsername()).get().getPassword();

        boolean isPasswordMatch = bCryptPasswordEncoder.matches(inputUserPassword, dbPassword);
        if (!isPasswordMatch) {
            return "shop-login";
        }

        orderDetailRepository.deleteAll();
        Order order = new Order();
        order.setId(1);
        order.setShippedFrom(locationRepository.getById(1));
        order.setCustomer(customerRepository.getById(1));
        order.setAddress(addressRepository.getById(1));
        order.setCreatedAt(LocalDateTime.of(LocalDate.of(2022, 06, 22), LocalTime.of(12, 30, 00)));
        return "redirect:/index";
    }
}
