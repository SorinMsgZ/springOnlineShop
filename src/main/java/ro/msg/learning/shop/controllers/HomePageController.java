package ro.msg.learning.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.services.CustomerService;

@RestController
public class HomePageController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String successfulRedirectToHomePageAfterOAuth2GithubAuthentication() {
        return customerService.returnHomePageMessageAfterOAuth2Github();
    }
}
