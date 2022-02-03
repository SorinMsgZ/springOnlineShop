package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.CustomerDTO;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.CustomerRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{
    private final CustomerRepository customerRepository;

    public List<CustomerDTO> listAll() {
        return customerRepository.findAll().stream()
                .map(CustomerDTO::of)
                .collect(Collectors.toList());
    }

    public CustomerDTO readByFirstName(String firstName) {
        return customerRepository.findByFirstName(firstName)
                .map(CustomerDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public CustomerDTO create(CustomerDTO input) {
        Customer customer = input.toEntity();
        return CustomerDTO.of(customerRepository.save(customer));
    }

    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }

    public CustomerDTO updateById(int id, CustomerDTO input) {
        Customer customer = customerRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(customer);
        customerRepository.save(customer);
        return CustomerDTO.of(customer);
    }

    @Override
    public String login(String username, String password) {
        Optional<Customer> customer = customerRepository.login(username,password);
        if(customer.isPresent()){
            String token = UUID.randomUUID().toString();
            Customer custom= customer.get();
            custom.setToken(token);
            customerRepository.save(custom);
            return token;
        }

        return StringUtils.EMPTY;
    }

    @Override
    public Optional<User> findByToken(String token) {
        Optional<Customer> customer= customerRepository.findByToken(token);
        if(customer.isPresent()){
            Customer customer1 = customer.get();
            User user= new User(customer1.getUserName(), customer1.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return  Optional.empty();
    }

    @Override
    public Customer findById(Integer id) {
        Optional<Customer> customer= customerRepository.findById(id);
        return customer.orElse(null);
    }
}
