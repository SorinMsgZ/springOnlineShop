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
import ro.msg.learning.shop.services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @BeforeEach
    void createProductDTO() {
        ProductCategoryDTO productCategoryOne =
                new ProductCategoryDTO(1, "Retaining Wall and Brick Pavers", "ProdCatDescription1");
        ProductCategoryDTO productCategoryTwo =
                new ProductCategoryDTO(2, "Drywall & Acoustical (MOB)", "ProdCatDescription2");
        productCategoryService.createProductCategory(productCategoryOne);
        productCategoryService.createProductCategory(productCategoryTwo);

        SupplierDTO supplierOne = new SupplierDTO(1, "Fisher-Huels");
        SupplierDTO supplierTwo = new SupplierDTO(2, "D Amore, Torp and Kuvalis");
        supplierService.createSupplier(supplierOne);
        supplierService.createSupplier(supplierTwo);

        ProductDTO productOne =
                new ProductDTO(1, "Fimbristylis vahlii (Lam.) Link", "quis orci", new BigDecimal("7.4"), 779, 1, "Retaining Wall and Brick Pavers", "ProdCatDescription1", 1, "Fisher-Huels", "http://dummyimage.com/223x100.png/ff4444/ffffff");

        ProductDTO productTwo =
                new ProductDTO(2, "Mahonia Nutt., pulvinar", "pulvinar", new BigDecimal("21.96"), 21.96, 2, "Drywall & Acoustical (MOB)", "ProdCatDescription2", 2, "D Amore, Torp and Kuvalis", "http://22dummyimage.com/104x100.png/ff4444/ffffff");
        productService.createProduct(productOne);
        productService.createProduct(productTwo);

        AddressDTO deliveryAddress = new AddressDTO(1, "United States", "Rochester", "New York", "440 Merry Drive");
        addressService.createAddress(deliveryAddress);

        LocationDTO locationOne = new LocationDTO(1, "cbslocal.com", deliveryAddress.toEntity());
        LocationDTO locationTwo = new LocationDTO(2, "msn.com", deliveryAddress.toEntity());
        LocationDTO locationThree = new LocationDTO(3, "sdgsg", deliveryAddress.toEntity());
        LocationDTO locationFour = new LocationDTO(4, "jdjtzk", deliveryAddress.toEntity());

        locationService.createLocation(locationOne);
        locationService.createLocation(locationTwo);
        locationService.createLocation(locationThree);
        locationService.createLocation(locationFour);

        StockDTO stockOne = new StockDTO(1, 1, productOne.toEntity(), locationOne.toEntity(), 10);
        StockDTO stockTwo = new StockDTO(2, 2, productTwo.toEntity(), locationTwo.toEntity(), 20);
        StockDTO stockThree = new StockDTO(1, 3, productOne.toEntity(), locationThree.toEntity(), 11);
        StockDTO stockFour = new StockDTO(2, 4, productTwo.toEntity(), locationFour.toEntity(), 21);

        stockService.createStock(stockOne);
        stockService.createStock(stockTwo);
        stockService.createStock(stockThree);
        stockService.createStock(stockFour);
    }

    @Test
    void testCreateOrder(@Value("${strategy.findLocation}") String strategy) {

        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(2, 2);
        StockId stockId3 = new StockId(1, 3);
        StockId stockId4 = new StockId(2, 4);

        int stock1Prod1Qty = stockService.readSingleStock(stockId1).getQuantity();
        int stock2Prod2Qty = stockService.readSingleStock(stockId2).getQuantity();
        int stock3Prod1Qty = stockService.readSingleStock(stockId3).getQuantity();
        int stock4Prod2Qty = stockService.readSingleStock(stockId4).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 20;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        orderObjectInputDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 2, 21), LocalTime.of(12, 30, 0)));
        AddressDTO deliveryAddress = addressService.listAddress().get(0);
        orderObjectInputDTO.setDeliveryAddress(deliveryAddress.toEntity());
        orderObjectInputDTO.setProduct(listProductWanted);

        int initialOrderNb = orderService.listOrder().size();

        List<OrderDTO> orderListCreatedDTO = orderCreatorController.createOrder(orderObjectInputDTO);
        List<Order> orderListCreated = orderListCreatedDTO.stream().map(OrderDTO::toEntity).collect(Collectors.toList());

        int actualOrderNb = orderService.listOrder().size();

        if (strategy.equals("SingleLocationStrategy")) {
            int expectUpdateStock1Prod1Qty = stock1Prod1Qty - testProd1Qty;
            int actualStock1Prod1Qty = stockService.readSingleStock(stockId1).getQuantity();

            int expectUpdateStock2Prod2Qty = stock2Prod2Qty - testProd2Qty;
            int actualStock2Prod2Qty = stockService.readSingleStock(stockId2).getQuantity();

            Assert.assertEquals(expectUpdateStock1Prod1Qty, actualStock1Prod1Qty);
            Assert.assertEquals(expectUpdateStock2Prod2Qty, actualStock2Prod2Qty);

        } else if (strategy.equals("MoreAbundantStrategy")) {
            int expectUpdateStock3Prod1Qty = stock3Prod1Qty - testProd1Qty;
            int actualStock3Prod1Qty = stockService.readSingleStock(stockId3).getQuantity();

            int expectUpdateStock4Prod2Qty = stock4Prod2Qty - testProd2Qty;
            int actualStock4Prod2Qty = stockService.readSingleStock(stockId4).getQuantity();

            Assert.assertEquals(expectUpdateStock3Prod1Qty, actualStock3Prod1Qty);
            Assert.assertEquals(expectUpdateStock4Prod2Qty, actualStock4Prod2Qty);
        }

        Assert.assertEquals(initialOrderNb + 2, actualOrderNb);
        for (Order order:orderListCreated) {
            System.out.println(order.toString());
        }
    }
}