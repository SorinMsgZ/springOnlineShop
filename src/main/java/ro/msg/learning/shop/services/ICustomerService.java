package ro.msg.learning.shop.services;

import org.springframework.security.core.userdetails.User;
import ro.msg.learning.shop.entities.Customer;

import java.util.Optional;

public interface ICustomerService {

    String login(String username, String password);
    Optional<User> findByToken(String token);
    Customer findById(Integer id);
}
