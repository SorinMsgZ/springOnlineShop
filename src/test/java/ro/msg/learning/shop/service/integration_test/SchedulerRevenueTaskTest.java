package ro.msg.learning.shop.service.integration_test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Customer;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("TestProfile3")
public class SchedulerRevenueTaskTest {

    @Autowired
    private SchedulerRevenueTask schedulerRevenueTask;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private RevenueService revenueService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    public void populateDataBase() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Ghita")
                .build();
        customerService.create(customerDTO);
        Customer customer = customerRepository.findByFirstName("Ghita").orElseThrow();

        LocationDTO locationDTO1 = LocationDTO.builder()
                .name("location1")
                .build();
        LocationDTO locationDTO2 = LocationDTO.builder()
                .name("location2")
                .build();
        locationService.create(locationDTO1);
        locationService.create(locationDTO2);

        Location location1 = locationRepository.findByName("location1").orElseThrow();
        Location location2 = locationRepository.findByName("location2").orElseThrow();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime olderDateTime = LocalDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(1, 1, 1));


        OrderDTO orderDTO1 = OrderDTO.builder()
                .shippedFrom(location1)
                .createdAt(localDateTime)
                .build();
        OrderDTO orderDTO2 = OrderDTO.builder()
                .shippedFrom(location1)
                .createdAt(localDateTime)
                .build();
        OrderDTO orderDTO3 = OrderDTO.builder()
                .shippedFrom(location2)
                .createdAt(localDateTime)
                .build();
        OrderDTO orderDTOOld4 = OrderDTO.builder()
                .shippedFrom(location2)
                .createdAt(olderDateTime)
                .build();
        orderService.create(orderDTO1);
        orderService.create(orderDTO2);
        orderService.create(orderDTO3);
        orderService.create(orderDTOOld4);

        Order order1 = orderRepository.findById(1).orElseThrow();
        Order order2 = orderRepository.findById(2).orElseThrow();
        Order order3 = orderRepository.findById(3).orElseThrow();
        Order orderOld4 = orderRepository.findById(4).orElseThrow();

        ProductDTO productDTO1 = ProductDTO.builder()
                .name("productName1")
                .price(new BigDecimal(10))
                .build();
        ProductDTO productDTO2 = ProductDTO.builder()
                .name("productName2")
                .price(new BigDecimal(5))
                .build();

        productService.create(productDTO1);
        productService.create(productDTO2);

        Product product1 = productRepository.findById(1).orElseThrow();
        Product product2 = productRepository.findById(2).orElseThrow();


        OrderDetailDTO orderDetailDTO1 = OrderDetailDTO.builder()
                .order(order1)
                .product(product1)
                .quantity(1)
                .build();
        OrderDetailDTO orderDetailDTO2 = OrderDetailDTO.builder()
                .order(order1)
                .product(product2)
                .quantity(2)
                .build();
        OrderDetailDTO orderDetailDTO3 = OrderDetailDTO.builder()
                .order(order2)
                .product(product1)
                .quantity(1)
                .build();
        OrderDetailDTO orderDetailDTO4 = OrderDetailDTO.builder()
                .order(order3)
                .product(product2)
                .quantity(1)
                .build();
        OrderDetailDTO orderDetailDTOOld5 = OrderDetailDTO.builder()
                .order(orderOld4)
                .product(product2)
                .quantity(1)
                .build();
        orderDetailService.create(orderDetailDTO1);
        orderDetailService.create(orderDetailDTO2);
        orderDetailService.create(orderDetailDTO3);
        orderDetailService.create(orderDetailDTO4);
        orderDetailService.create(orderDetailDTOOld5);
    }


    @Test
    public void testAggregateAndStoreSalesRevenues() throws InterruptedException {

        populateDataBase();

        Thread.sleep(5000);

        int nbOfRevenues = schedulerRevenueTask.getRevenueList().size();
        assertThat(nbOfRevenues).isPositive();

        Assert.assertEquals(30,schedulerRevenueTask.getRevenueList().get(0).getSum().intValue());
        Assert.assertEquals(5,schedulerRevenueTask.getRevenueList().get(1).getSum().intValue());

    }
}
