package ro.msg.learning.shop.controller.integrationTest;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.controllers.OrderCreatorController;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.exceptions.NoSuitableLocationsFound;
import ro.msg.learning.shop.services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderCreatorControllerTest {

    @Autowired
    private OrderCreatorController orderCreatorController;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductService productService;
    @Autowired
    AddressService addressService;
    @Autowired
    LocationService locationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StockService stockService;
    private ProductDTO productOne;
    private ProductDTO productTwo;
    private Location locOne;
    private Location locTwo;
    private Location locThree;
    private Location locDelivery;

    @BeforeEach
    void createProductDTO() {
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

        productCategoryService.create(productCategoryOne);
        productCategoryService.create(productCategoryTwo);

        SupplierDTO supplierOne = SupplierDTO.builder()
                .name("Fisher-Huels")
                .build();
        SupplierDTO supplierTwo = SupplierDTO.builder()
                .name("D Amore, Torp and Kuvalis")
                .build();
        supplierService.create(supplierOne);
        supplierService.create(supplierTwo);

        Supplier sup1 = supplierOne.toEntity();
        Supplier sup2 = supplierTwo.toEntity();

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

        productService.create(productOne);
        productService.create(productTwo);

        AddressDTO stockAddressDTO1 = AddressDTO.builder()
                .country("United States")
                .city("Westminster")
                .county("Westminster")
                .streetAddress("streetAddress1")
                .state("CO")
                .build();
        addressService.create(stockAddressDTO1);
        Address stockAddress1 = stockAddressDTO1.toEntity();
        stockAddress1.setId(1);

        AddressDTO stockAddressDTO2 = AddressDTO.builder()
                .country("United States")
                .city("Denver")
                .county("Denver")
                .streetAddress("440 Merry Drive")
                .state("CO")
                .build();
        addressService.create(stockAddressDTO2);
        Address stockAddress2 = stockAddressDTO2.toEntity();
        stockAddress2.setId(2);

        AddressDTO stockAddressDTO3 = AddressDTO.builder()
                .country("United States")
                .city("Portland")
                .county("Portland")
                .streetAddress("streetAddress3")
                .state("OR")
                .build();
        addressService.create(stockAddressDTO3);
        Address stockAddress3 = stockAddressDTO3.toEntity();
        stockAddress3.setId(3);

        AddressDTO deliveryAddressDTO = AddressDTO.builder()
                .country("United States")
                .city("Boulder")
                .county("Boulder")
                .streetAddress("streetAddressX")
                .state("CO")
                .build();
        addressService.create(deliveryAddressDTO);
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

        locationService.create(locationOne);
        locationService.create(locationTwo);
        locationService.create(locationThree);
        locationService.create(deliveryLocation);

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
    void testCreateOrderSuccessfullyWhenLocationsAreFoundAndHaveAllEnoughStockQuantity(
            @Value("${strategy.findLocation}") String strategy) {
        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productOne.toEntity(), locTwo, 11);
        StockDTO stockThree = new StockDTO(productOne.toEntity(), locThree, 12);
        StockDTO stockFour = new StockDTO(productTwo.toEntity(), locOne, 10);
        StockDTO stockFive = new StockDTO(productTwo.toEntity(), locTwo, 11);

        stockService.create(stockOne);
        stockService.create(stockTwo);
        stockService.create(stockThree);
        stockService.create(stockFour);
        stockService.create(stockFive);
        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(1, 2);
        StockId stockId3 = new StockId(1, 3);
        StockId stockId4 = new StockId(2, 1);
        StockId stockId5 = new StockId(2, 2);

        int stock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
        int stock2Prod1Loc2Qty = stockService.readById(stockId2).getQuantity();
        int stock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();
        int stock4Prod2Loc1Qty = stockService.readById(stockId4).getQuantity();
        int stock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 10;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        orderObjectInputDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 2, 21), LocalTime.of(12, 30, 0)));

        orderObjectInputDTO.setDeliveryAddress(locDelivery.getAddress());
        orderObjectInputDTO.setProduct(listProductWanted);

        int initialOrderNb = orderService.listAll().size();

        orderCreatorController.create(orderObjectInputDTO);

        int actualOrderNb = orderService.listAll().size();

        if (strategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            int expectUpdateStock1Prod1Loc1Qty = stock1Prod1Loc1Qty - testProd1Qty;
            int actualStock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();

            int expectUpdateStock4Prod2Loc1Qty = stock4Prod2Loc1Qty - testProd2Qty;
            int actualStock4Prod2Loc1Qty = stockService.readById(stockId4).getQuantity();

            Assert.assertEquals(expectUpdateStock1Prod1Loc1Qty, actualStock1Prod1Loc1Qty);
            Assert.assertEquals(expectUpdateStock4Prod2Loc1Qty, actualStock4Prod2Loc1Qty);

        } else if (strategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            int expectUpdateStock3Prod1Loc3Qty = stock3Prod1Loc3Qty - testProd1Qty;
            int actualStock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();

            int expectUpdateStock5Prod2Loc2Qty = stock5Prod2Loc2Qty - testProd2Qty;
            int actualStock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

            Assert.assertEquals(expectUpdateStock3Prod1Loc3Qty, actualStock3Prod1Loc3Qty);
            Assert.assertEquals(expectUpdateStock5Prod2Loc2Qty, actualStock5Prod2Loc2Qty);
        } else if (strategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            int expectUpdateStock1Prod1Loc1Qty = stock1Prod1Loc1Qty - testProd1Qty;
            int actualStock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();

            int expectUpdateStock4Prod2Loc1Qty = stock4Prod2Loc1Qty - testProd2Qty;
            int actualStock4Prod2Loc1Qty = stockService.readById(stockId4).getQuantity();

            Assert.assertEquals(expectUpdateStock1Prod1Loc1Qty, actualStock1Prod1Loc1Qty);
            Assert.assertEquals(expectUpdateStock4Prod2Loc1Qty, actualStock4Prod2Loc1Qty);
        }
        Assert.assertEquals(initialOrderNb + 2, actualOrderNb);
    }

    @Test
    void testCreateOrderSuccessfullyWhenLocationsAreFoundButNotAllOfThemWithEnoughStockQuantity(
            @Value("${strategy.findLocation}") String strategy) {
        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productOne.toEntity(), locTwo, 11);
        StockDTO stockThree = new StockDTO(productOne.toEntity(), locThree, 12);
        StockDTO stockFour = new StockDTO(productTwo.toEntity(), locOne, 9);
        StockDTO stockFive = new StockDTO(productTwo.toEntity(), locTwo, 11);

        stockService.create(stockOne);
        stockService.create(stockTwo);
        stockService.create(stockThree);
        stockService.create(stockFour);
        stockService.create(stockFive);
        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(1, 2);
        StockId stockId3 = new StockId(1, 3);
        StockId stockId4 = new StockId(2, 1);
        StockId stockId5 = new StockId(2, 2);

        int stock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
        int stock2Prod1Loc2Qty = stockService.readById(stockId2).getQuantity();
        int stock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();
        int stock4Prod2Loc1Qty = stockService.readById(stockId4).getQuantity();
        int stock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 10;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        orderObjectInputDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 2, 21), LocalTime.of(12, 30, 0)));
        AddressDTO deliveryAddress = addressService.listAll().get(3);
        Address delAddressInput = deliveryAddress.toEntity();
        delAddressInput.setId(1);
        orderObjectInputDTO.setDeliveryAddress(delAddressInput);
        orderObjectInputDTO.setProduct(listProductWanted);

        int initialOrderNb = orderService.listAll().size();

        orderCreatorController.create(orderObjectInputDTO);

        int actualOrderNb = orderService.listAll().size();

        if (strategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            int expectUpdateStock2Prod1Loc2Qty = stock2Prod1Loc2Qty - testProd1Qty;
            int actualStock2Prod1Loc2Qty = stockService.readById(stockId2).getQuantity();

            int expectUpdateStock5Prod2Loc2Qty = stock5Prod2Loc2Qty - testProd2Qty;
            int actualStock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

            Assert.assertEquals(expectUpdateStock2Prod1Loc2Qty, actualStock2Prod1Loc2Qty);
            Assert.assertEquals(expectUpdateStock5Prod2Loc2Qty, actualStock5Prod2Loc2Qty);

        } else if (strategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            int expectUpdateStock3Prod1Loc3Qty = stock3Prod1Loc3Qty - testProd1Qty;
            int actualStock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();

            int expectUpdateStock5Prod2Loc2Qty = stock5Prod2Loc2Qty - testProd2Qty;
            int actualStock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

            Assert.assertEquals(expectUpdateStock3Prod1Loc3Qty, actualStock3Prod1Loc3Qty);
            Assert.assertEquals(expectUpdateStock5Prod2Loc2Qty, actualStock5Prod2Loc2Qty);

        } else if (strategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            int expectUpdateStock1Prod1Loc1Qty = stock1Prod1Loc1Qty - testProd1Qty;
            int actualStock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();

            int expectUpdateStock5Prod2Loc2Qty = stock5Prod2Loc2Qty - testProd2Qty;
            int actualStock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

            Assert.assertEquals(expectUpdateStock1Prod1Loc1Qty, actualStock1Prod1Loc1Qty);
            Assert.assertEquals(expectUpdateStock5Prod2Loc2Qty, actualStock5Prod2Loc2Qty);
        }
        Assert.assertEquals(initialOrderNb + 2, actualOrderNb);
    }

    @Test
    void testFailCreateOrderWhenLocationsAreFoundButForOneProductNotEnoughStockQuantity(
            @Value("${strategy.findLocation}") String strategy) {
        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productOne.toEntity(), locTwo, 11);
        StockDTO stockThree = new StockDTO(productOne.toEntity(), locThree, 12);
        StockDTO stockFour = new StockDTO(productTwo.toEntity(), locOne, 9);
        StockDTO stockFive = new StockDTO(productTwo.toEntity(), locTwo, 11);

        stockService.create(stockOne);
        stockService.create(stockTwo);
        stockService.create(stockThree);
        stockService.create(stockFour);
        stockService.create(stockFive);
        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(1, 2);
        StockId stockId3 = new StockId(1, 3);
        StockId stockId4 = new StockId(2, 1);
        StockId stockId5 = new StockId(2, 2);

        int stock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
        int stock2Prod1Loc2Qty = stockService.readById(stockId2).getQuantity();
        int stock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();
        int stock4Prod2Loc1Qty = stockService.readById(stockId4).getQuantity();
        int stock5Prod2Loc2Qty = stockService.readById(stockId5).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 100;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        orderObjectInputDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 2, 21), LocalTime.of(12, 30, 0)));
        AddressDTO deliveryAddress = addressService.listAll().get(3);
        Address delAddressInput = deliveryAddress.toEntity();
        delAddressInput.setId(1);
        orderObjectInputDTO.setDeliveryAddress(delAddressInput);
        orderObjectInputDTO.setProduct(listProductWanted);

        int initialOrderNb = orderService.listAll().size();
        int actualOrderNb;
        try {
            orderCreatorController.create(orderObjectInputDTO);
            actualOrderNb = orderService.listAll().size();
        }
        catch (NoSuitableLocationsFound ex){
            actualOrderNb = orderService.listAll().size();
            Assert.assertEquals(initialOrderNb, actualOrderNb);
            }

        if (strategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            int actualStock2Prod1Loc2Qty = stockService.readById(stockId2).getQuantity();
            Assert.assertEquals(stock2Prod1Loc2Qty, actualStock2Prod1Loc2Qty);


        } else if (strategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            int actualStock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();
            Assert.assertEquals(stock3Prod1Loc3Qty, actualStock3Prod1Loc3Qty);

        } else if (strategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            int actualStock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
            Assert.assertEquals(stock1Prod1Loc1Qty, actualStock1Prod1Loc1Qty);
        }
        Assert.assertEquals(initialOrderNb, actualOrderNb);
    }
    @Test
    void testFailCreateOrderWhenForOneProductNoStockLocationAvailable(
            @Value("${strategy.findLocation}") String strategy) {
        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productOne.toEntity(), locTwo, 11);
        StockDTO stockThree = new StockDTO(productOne.toEntity(), locThree, 12);

        stockService.create(stockOne);
        stockService.create(stockTwo);
        stockService.create(stockThree);

        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(1, 2);
        StockId stockId3 = new StockId(1, 3);

        int stock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
        int stock2Prod1Loc2Qty = stockService.readById(stockId2).getQuantity();
        int stock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 10;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        orderObjectInputDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 2, 21), LocalTime.of(12, 30, 0)));

        orderObjectInputDTO.setDeliveryAddress(locDelivery.getAddress());
        orderObjectInputDTO.setProduct(listProductWanted);

        int initialOrderNb = orderService.listAll().size();

        int actualOrderNb;
        try {
            orderCreatorController.create(orderObjectInputDTO);
            actualOrderNb = orderService.listAll().size();
        }
        catch (NoSuitableLocationsFound ex){
            actualOrderNb = orderService.listAll().size();
            Assert.assertEquals(initialOrderNb, actualOrderNb);
        }

        if (strategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            int actualStock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
            Assert.assertEquals(stock1Prod1Loc1Qty, actualStock1Prod1Loc1Qty);

        } else if (strategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            int actualStock3Prod1Loc3Qty = stockService.readById(stockId3).getQuantity();
            Assert.assertEquals(stock3Prod1Loc3Qty, actualStock3Prod1Loc3Qty);

        } else if (strategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            int actualStock1Prod1Loc1Qty = stockService.readById(stockId1).getQuantity();
            Assert.assertEquals(stock1Prod1Loc1Qty, actualStock1Prod1Loc1Qty);
        }
        Assert.assertEquals(initialOrderNb , actualOrderNb);
    }
}
