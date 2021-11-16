package ro.msg.learning.shop.repositories;

import org.springframework.data.repository.CrudRepository;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Customer;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
