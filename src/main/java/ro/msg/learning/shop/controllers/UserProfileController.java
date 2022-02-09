package ro.msg.learning.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.services.CustomerService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/user/{id}",produces = "application/json")
    public Customer getUserDetail(@PathVariable Integer id){
        return customerService.findById(id);
    }
}
