package ro.msg.learning.shop.repository.integrationTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.ShopApplication;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.AddressRepository;
import ro.msg.learning.shop.repositories.CustomerRepository;
import ro.msg.learning.shop.repositories.LocationRepository;
import ro.msg.learning.shop.repositories.OrderRepository;
import ro.msg.learning.shop.testprofileConfigureSeparateDataSource.OrderJpaConfig;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ShopApplication.class,
        OrderJpaConfig.class})
@ActiveProfiles("TestProfile1")

public class CreateOrderTest {
    @Resource
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void testCreateOrderSuccessfully() {

        Address address = new Address();

        address.setCountry("Romania");
        address.setCounty("Alabama");
        address.setCity("Bronx");
        address.setStreetAddress("93 Drewry Junction");
        addressRepository.save(address);

        Location location = new Location();

        location.setName("msn.com");
        location.setAddress(address);
        locationRepository.save(location);

        Customer customer = new Customer();

        customer.setFirstName("Cliff");
        customer.setLastName("Guerrieri");
        customer.setEmailAddress("cguerrieri0@dropbox.com");
        customer.setUserName("cguerrieri0");
        customer.setPassword("GSxlFi1MPR");
        customerRepository.save(customer);

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2020, 10, 20), LocalTime.of(12, 30, 0));

        Order order = new Order();

        order.setAddress(address);
        order.setCreatedAt(localDateTime);
        order.setShippedFrom(location);
        order.setCustomer(customer);
        orderRepository.save(order);

        int idOrder = order.getId();
        Order order2 = orderRepository.findById(1).orElseThrow(NotFoundException::new);
        assertEquals(idOrder, order2.getId());
    }
}
