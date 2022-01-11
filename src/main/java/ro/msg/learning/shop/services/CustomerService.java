package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.CustomerDTO;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.CustomerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
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
}
