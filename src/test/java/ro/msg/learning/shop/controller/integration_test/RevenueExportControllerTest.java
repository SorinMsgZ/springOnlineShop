package ro.msg.learning.shop.controller.integration_test;

import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.*;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles({"with-base", "TestProfile3"})
public class RevenueExportControllerTest {
    @Autowired
    private MockMvc mvc;
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
    @Autowired
    UserRepository userRepository;
    private LocalDate localDateToTest;
    User userTest;
    private String activeProfile;
    @Autowired
    private Environment environment;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository  addressRepository;

    public void populateDataBase() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstName("Ghita")
                .build();
        customerService.create(customerDTO);
        Customer customer = customerRepository.findByFirstName("Ghita").orElseThrow();

        AddressDTO stockAddressDTO1 = AddressDTO.builder()
                .country("United States")
                .city("Westminster")
                .county("Westminster")
                .streetAddress("streetAddress1")
                .state("CO")
                .build();
        AddressDTO stockAddressDTO2 = AddressDTO.builder()
                .country("United States")
                .city("Westminster")
                .county("Westminster")
                .streetAddress("streetAddress2")
                .state("CO")
                .build();
        addressService.create(stockAddressDTO1);
        addressService.create(stockAddressDTO2);

        Address address1 = addressRepository.findById(1).orElseThrow();
        Address address2 = addressRepository.findById(2).orElseThrow();

        LocationDTO locationDTO1 = LocationDTO.builder()
                .name("location1")
                .address(address1)
                .build();
        LocationDTO locationDTO2 = LocationDTO.builder()
                .name("location2")
                .address(address2)
                .build();
        locationService.create(locationDTO1);
        locationService.create(locationDTO2);

        Location location1 = locationRepository.findByName("location1").orElseThrow();
        Location location2 = locationRepository.findByName("location2").orElseThrow();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime olderDateTime = LocalDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(1, 1, 1));
        localDateToTest = localDateTime.toLocalDate();

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

        activeProfile = Arrays.toString(environment.getActiveProfiles());

        Customer customer2 =   new Customer();
        customer2.setFirstName("Cliff");
        customer2.setLastName("Clifescu");
        customer2.setEmailAddress("clif@clif.com");
        customer2.setUserName("clifut");
        customer2.setPassword("example123");
        customer2.setId(1);
        customerRepository.save(customer2);

        userTest = new User();
        userTest.setCustomer(customer2);
        userTest.setUsername("cliff");

        if (activeProfile.equals("[with-base, TestProfile3]")) {
            userTest.setPassword("{bcrypt}$2a$10$c1UdwXGZf97TYBkA8ZGquuf6WZI.zl6LAmAw3tMdMKEc94ifWrKEa");
        } else if (activeProfile.equals("[with-form, TestProfile3]")) {
            userTest.setPassword("$2a$10$c1UdwXGZf97TYBkA8ZGquuf6WZI.zl6LAmAw3tMdMKEc94ifWrKEa");
        }
        userRepository.save(userTest);
    }


    @Test
    public void testReadRevenuesByLocalDateStringAfterAggregateAndStoreSalesRevenuesByTaskScheduler() throws Exception {

        populateDataBase();

        Thread.sleep(10000);

        int nbOfRevenues = schedulerRevenueTask.getRevenueList().size();
        assertThat(nbOfRevenues).isPositive();

        Assert.assertEquals(30, schedulerRevenueTask.getRevenueList().get(0).getSum().intValue());
        Assert.assertEquals(5, schedulerRevenueTask.getRevenueList().get(1).getSum().intValue());


        RevenueExportDTO revenueExportDTO1 = RevenueExportDTO.builder()
                .locationId(schedulerRevenueTask.getRevenueList().get(0).getLocation().getId())
                .localDateString(schedulerRevenueTask.getRevenueList().get(0).getLocalDate().toString())
                .sumInt(schedulerRevenueTask.getRevenueList().get(0).getSum().intValue())
                .build();
        RevenueExportDTO revenueExportDTO2 = RevenueExportDTO.builder()
                .locationId(schedulerRevenueTask.getRevenueList().get(1).getLocation().getId())
                .localDateString(schedulerRevenueTask.getRevenueList().get(1).getLocalDate().toString())
                .sumInt(schedulerRevenueTask.getRevenueList().get(1).getSum().intValue())
                .build();

        List<RevenueExportDTO> revenueExportDTOList = new ArrayList<>();
        revenueExportDTOList.add(revenueExportDTO1);
        revenueExportDTOList.add(revenueExportDTO2);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(RevenueExportDTO.class);
        StringWriter strW = new StringWriter();
        SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
        seqW.write(revenueExportDTOList);
        seqW.close();

        String headerOfCsvText = "location,localDate,sum" + "\n";
        String expectedTwoRevenueExportDTOAsCsvText = headerOfCsvText + strW.toString();

        mvc.perform(MockMvcRequestBuilders.get("/api/revenues/export/csv/" + localDateToTest.toString())
                .with(httpBasic("cliff", "example123")))
                .andExpect(authenticated().withUsername("cliff"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(content()
                        .string(containsString(expectedTwoRevenueExportDTOAsCsvText)))
                .andReturn();

    }
}
