package ro.msg.learning.shop.controller.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.controllers.*;

import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.StockId;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.services.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderCreatorRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderCreatorService orderCreatorService;
    @Autowired
    ProductCategoryController productCategoryController;
    @Autowired
    SupplierController supplierController;
    @Autowired
    ProductController productController;
    @Autowired
    AddressController addressController;
    @Autowired
    LocationController locationController;
    @Autowired
    private StockController stockController;
    @Autowired
    private OrderCreatorController orderCreatorController;
    private ProductDTO productOne;
    private ProductDTO productTwo;
    private Location locOne;
    private Location locTwo;
    private Location locThree;
    private Location locDelivery;
    @Autowired
    private CustomerService customerService;
    @Value("${strategy.findLocation}")
    private String strategy;

    @Before
    public void mockOneProductDTO() {
        CustomerDTO mockCustomer = CustomerDTO.builder()
                .firstName("MockCustomerFirstName")
                .lastName("MockCustomerLastName")
                .emailAddress("MockCustomerEmailAddress")
                .build();
        customerService.create(mockCustomer);

        ProductCategoryDTO productCategoryOne =
                ProductCategoryDTO.builder()
                        .name("Retaining Wall and Brick Pavers")
                        .description("ProdCatDescription1")
                        .build();

        ProductCategoryDTO productCategoryTwo =
                ProductCategoryDTO.builder()
                        .name("Drywall & Acoustical (MOB)")
                        .description("ProdCatDescription2")
                        .build();

        productCategoryController.create(productCategoryOne);
        productCategoryController.create(productCategoryTwo);

        SupplierDTO supplierOne = SupplierDTO.builder()
                .name("Fisher-Huels")
                .build();
        SupplierDTO supplierTwo = SupplierDTO.builder()
                .name("D Amore, Torp and Kuvalis")
                .build();
        supplierController.create(supplierOne);
        supplierController.create(supplierTwo);

        Supplier sup1 = supplierOne.toEntity();
        sup1.setId(1);

        Supplier sup2 = supplierTwo.toEntity();
        sup2.setId(2);

        productOne = ProductDTO.builder()
                .id(1)
                .name("Fimbristylis vahlii (Lam.) Link")
                .description("quis orci")
                .price(new BigDecimal("7.4"))
                .weight(100)
                .productCategoryId(1)
                .productCategoryName("Retaining Wall and Brick Pavers")
                .productCategoryDescription("ProdCatDescription1")
                .supplier(sup1)
                .imageUrl("http://11dummyimage.com/223x100.png/ff4444/ffffff")
                .build();

        productTwo = ProductDTO.builder()
                .id(2)
                .name("Mahonia Nutt., pulvinar")
                .description("pulvinar")
                .price(new BigDecimal("21.96"))
                .weight(200.20)
                .productCategoryId(2)
                .productCategoryName("Drywall & Acoustical (MOB)")
                .productCategoryDescription("ProdCatDescription2")
                .supplier(sup2)
                .imageUrl("http://22dummyimage.com/104x100.png/ff4444/ffffff")
                .build();

        productController.create(productOne);
        productController.create(productTwo);

        AddressDTO stockAddressDTO1 = AddressDTO.builder()
                .country("United States")
                .city("Westminster")
                .county("Westminster")
                .streetAddress("streetAddress1")
                .state("CO")
                .build();
        addressController.create(stockAddressDTO1);
        Address stockAddress1 = stockAddressDTO1.toEntity();
        stockAddress1.setId(1);

        AddressDTO stockAddressDTO2 = AddressDTO.builder()
                .country("United States")
                .city("Denver")
                .county("Denver")
                .streetAddress("440 Merry Drive")
                .state("CO")
                .build();
        addressController.create(stockAddressDTO2);
        Address stockAddress2 = stockAddressDTO2.toEntity();
        stockAddress2.setId(2);

        AddressDTO stockAddressDTO3 = AddressDTO.builder()
                .country("United States")
                .city("Portland")
                .county("Portland")
                .streetAddress("streetAddress3")
                .state("OR")
                .build();
        addressController.create(stockAddressDTO3);
        Address stockAddress3 = stockAddressDTO3.toEntity();
        stockAddress3.setId(3);

        AddressDTO deliveryAddressDTO = AddressDTO.builder()
                .country("United States")
                .city("Boulder")
                .county("Boulder")
                .streetAddress("streetAddressX")
                .state("CO")
                .build();
        addressController.create(deliveryAddressDTO);
        Address deliveryAddress = deliveryAddressDTO.toEntity();
        deliveryAddress.setId(4);

        LocationDTO locationOne = LocationDTO.builder()
                .name("cbslocal.com")
                .address(stockAddress1)
                .build();
        LocationDTO locationTwo = LocationDTO.builder()
                .name("msn.com")
                .address(stockAddress2)
                .build();
        LocationDTO locationThree = LocationDTO.builder()
                .name("sdgsg")
                .address(stockAddress3)
                .build();
        LocationDTO deliveryLocation = LocationDTO.builder()
                .name("jdjtzk")
                .address(deliveryAddress)
                .build();

        locationController.create(locationOne);
        locationController.create(locationTwo);
        locationController.create(locationThree);
        locationController.create(deliveryLocation);

        locOne = locationOne.toEntity();
        locOne.setId(1);
        locTwo = locationTwo.toEntity();
        locTwo.setId(2);
        locThree = locationThree.toEntity();
        locThree.setId(3);
        locDelivery = deliveryLocation.toEntity();
        locDelivery.setId(4);
    }

    @Test
    public void testCreateOrderSuccessfullyWhenLocationsAreFoundAndHaveAllEnoughStockQuantity() throws Exception {
        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productOne.toEntity(), locTwo, 11);
        StockDTO stockThree = new StockDTO(productOne.toEntity(), locThree, 12);
        StockDTO stockFour = new StockDTO(productTwo.toEntity(), locOne, 10);
        StockDTO stockFive = new StockDTO(productTwo.toEntity(), locTwo, 11);

        stockController.create(stockOne);
        stockController.create(stockTwo);
        stockController.create(stockThree);
        stockController.create(stockFour);
        stockController.create(stockFive);
        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(1, 2);
        StockId stockId3 = new StockId(1, 3);
        StockId stockId4 = new StockId(2, 1);
        StockId stockId5 = new StockId(2, 2);

        int stock1Prod1Loc1Qty = stockController.readById(stockId1).getQuantity();
        int stock2Prod1Loc2Qty = stockController.readById(stockId2).getQuantity();
        int stock3Prod1Loc3Qty = stockController.readById(stockId3).getQuantity();
        int stock4Prod2Loc1Qty = stockController.readById(stockId4).getQuantity();
        int stock5Prod2Loc2Qty = stockController.readById(stockId5).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 10;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        LocalDateTimeDTO localDateTimeDTO = LocalDateTimeDTO.builder()
                .year(2021)
                .month(2)
                .dayOfMonth(21)
                .hour(12)
                .minute(30)
                .second(0)
                .build();
        orderObjectInputDTO.setCreatedAt(localDateTimeDTO);
        AddressDTO deliveryAddress = addressController.listAll().get(0);
        Address delAddressInput = deliveryAddress.toEntity();
        delAddressInput.setId(1);
        orderObjectInputDTO.setDeliveryAddress(delAddressInput);
        orderObjectInputDTO.setProduct(listProductWanted);


        int initialOrderNb = orderCreatorController.listAll().size();

        int expectedOrderLocationId1;
        int expectedOrderLocationId3;
        int expectedOrderLocationId4;
        int expectedOrderLocationId5;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String productAsStringDTO = objectMapper.writeValueAsString(orderObjectInputDTO);

        if (strategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            expectedOrderLocationId1 = stockId1.getLocationId();
            expectedOrderLocationId4 = stockId4.getLocationId();

            MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/orders/")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(productAsStringDTO)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0]shippedFrom.id").value(expectedOrderLocationId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[1]shippedFrom.id").value(expectedOrderLocationId4))
                    .andReturn();

            int idOrderOne =
                    JsonPath.read(result.getResponse().getContentAsString(), "$.[0]shippedFrom.id");
            int idOrderTwo =
                    JsonPath.read(result.getResponse().getContentAsString(), "$.[1]shippedFrom.id");

            Assert.assertEquals(expectedOrderLocationId1, idOrderOne);
            Assert.assertEquals(expectedOrderLocationId4, idOrderTwo);

        } else if (strategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            expectedOrderLocationId3 = stockId3.getLocationId();
            expectedOrderLocationId5 = stockId5.getLocationId();

            MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/orders/")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(productAsStringDTO)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0]shippedFrom.id").value(expectedOrderLocationId3))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[1]shippedFrom.id").value(expectedOrderLocationId5))
                    .andReturn();

            int idOrderOne =
                    JsonPath.read(result.getResponse().getContentAsString(), "$.[0]shippedFrom.id");
            int idOrderTwo =
                    JsonPath.read(result.getResponse().getContentAsString(), "$.[1]shippedFrom.id");
            Assert.assertEquals(expectedOrderLocationId3, idOrderOne);
            Assert.assertEquals(expectedOrderLocationId5, idOrderTwo);
        } else if (strategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            expectedOrderLocationId1 = stockId1.getLocationId();
            expectedOrderLocationId4 = stockId4.getLocationId();

            MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/orders/")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(productAsStringDTO)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[0]shippedFrom.id").value(expectedOrderLocationId1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.[1]shippedFrom.id").value(expectedOrderLocationId4))
                    .andReturn();

            int idOrderOne =
                    JsonPath.read(result.getResponse().getContentAsString(), "$.[0]shippedFrom.id");
            int idOrderTwo =
                    JsonPath.read(result.getResponse().getContentAsString(), "$.[1]shippedFrom.id");

            Assert.assertEquals(expectedOrderLocationId1, idOrderOne);
            Assert.assertEquals(expectedOrderLocationId4, idOrderTwo);
        }


        int actualOrderNb = orderCreatorController.listAll().size();

        Assert.assertEquals(initialOrderNb + 2, actualOrderNb);


        if (strategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            int expectUpdateStock1Prod1Qty = stock1Prod1Loc1Qty - testProd1Qty;
            int actualStock1Prod1Qty = stockController.readById(stockId1).getQuantity();

            int expectUpdateStock4Prod2Qty = stock4Prod2Loc1Qty - testProd2Qty;
            int actualStock4Prod2Qty = stockController.readById(stockId4).getQuantity();

            Assert.assertEquals(expectUpdateStock1Prod1Qty, actualStock1Prod1Qty);
            Assert.assertEquals(expectUpdateStock4Prod2Qty, actualStock4Prod2Qty);

        } else if (strategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            int expectUpdateStock3Prod1Qty = stock3Prod1Loc3Qty - testProd1Qty;
            int actualStock3Prod1Qty = stockController.readById(stockId3).getQuantity();

            int expectUpdateStock5Prod2Qty = stock5Prod2Loc2Qty - testProd2Qty;
            int actualStock5Prod2Qty = stockController.readById(stockId5).getQuantity();

            Assert.assertEquals(expectUpdateStock3Prod1Qty, actualStock3Prod1Qty);
            Assert.assertEquals(expectUpdateStock5Prod2Qty, actualStock5Prod2Qty);
        } else if (strategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            int expectUpdateStock1Prod1Qty = stock1Prod1Loc1Qty - testProd1Qty;
            int actualStock1Prod1Qty = stockController.readById(stockId1).getQuantity();

            int expectUpdateStock4Prod2Qty = stock4Prod2Loc1Qty - testProd2Qty;
            int actualStock4Prod2Qty = stockController.readById(stockId4).getQuantity();

            Assert.assertEquals(expectUpdateStock1Prod1Qty, actualStock1Prod1Qty);
            Assert.assertEquals(expectUpdateStock4Prod2Qty, actualStock4Prod2Qty);

        }
    }
}
