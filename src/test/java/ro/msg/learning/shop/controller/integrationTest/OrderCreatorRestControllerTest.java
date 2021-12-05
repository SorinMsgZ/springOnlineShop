package ro.msg.learning.shop.controller.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.controllers.OrderCreatorController;
import ro.msg.learning.shop.controllers.ProductController;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.StockId;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.services.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderCreatorRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderCreatorService orderCreatorService;
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
    @Autowired
    private OrderCreatorController orderCreatorController;

    @BeforeEach
    public void mockOneProductDTO() {
        ProductCategoryDTO productCategoryOne =
                new ProductCategoryDTO("Retaining Wall and Brick Pavers", "ProdCatDescription1");
        ProductCategoryDTO productCategoryTwo =
                new ProductCategoryDTO("Drywall & Acoustical (MOB)", "ProdCatDescription2");
        productCategoryService.create(productCategoryOne);
        productCategoryService.create(productCategoryTwo);

        SupplierDTO supplierOne = new SupplierDTO("Fisher-Huels");
        SupplierDTO supplierTwo = new SupplierDTO("D Amore, Torp and Kuvalis");
        supplierService.create(supplierOne);
        supplierService.create(supplierTwo);

        ProductDTO productOne = new ProductDTO();
        productOne.setId(1);
        productOne.setName("Fimbristylis vahlii (Lam.) Link");
        productOne.setDescription("quis orci");
        productOne.setPrice(new BigDecimal("7.4"));
        productOne.setWeight(100);
        productOne.setProductCategoryId(1);
        productOne.setProductCategoryName("Retaining Wall and Brick Pavers");
        productOne.setProductCategoryDescription("ProdCatDescription1");
        Supplier sup1 = supplierOne.toEntity();
        sup1.setId(1);
        productOne.setSupplier(sup1);
        productOne.setImageUrl("http://11dummyimage.com/223x100.png/ff4444/ffffff");

        ProductDTO productTwo = new ProductDTO();
        productTwo.setId(2);
        productTwo.setName("Mahonia Nutt., pulvinar");
        productTwo.setDescription("pulvinar");
        productTwo.setPrice(new BigDecimal("21.96"));
        productTwo.setWeight(200.20);
        productTwo.setProductCategoryId(2);
        productTwo.setProductCategoryName("Drywall & Acoustical (MOB)");
        productTwo.setProductCategoryDescription("ProdCatDescription2");
        Supplier sup2 = supplierTwo.toEntity();
        sup1.setId(2);
        productTwo.setSupplier(sup2);
        productTwo.setImageUrl("http://22dummyimage.com/104x100.png/ff4444/ffffff");


        productService.create(productOne);
        productService.create(productTwo);

        AddressDTO deliveryAddress = new AddressDTO("United States", "Rochester", "New York", "440 Merry Drive");
        addressService.create(deliveryAddress);

        Address delAddress = deliveryAddress.toEntity();
        delAddress.setId(1);


        LocationDTO locationOne = new LocationDTO("cbslocal.com", delAddress);
        LocationDTO locationTwo = new LocationDTO("msn.com", delAddress);
        LocationDTO locationThree = new LocationDTO("sdgsg", delAddress);
        LocationDTO locationFour = new LocationDTO("jdjtzk", delAddress);

        locationService.create(locationOne);
        locationService.create(locationTwo);
        locationService.create(locationThree);
        locationService.create(locationFour);

        Location locOne = locationOne.toEntity();
        locOne.setId(1);
        Location locTwo = locationTwo.toEntity();
        locTwo.setId(2);
        Location locThree = locationThree.toEntity();
        locThree.setId(3);
        Location locFour = locationFour.toEntity();
        locFour.setId(4);

        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productTwo.toEntity(), locTwo, 20);
        StockDTO stockThree = new StockDTO(productOne.toEntity(), locThree, 11);
        StockDTO stockFour = new StockDTO(productTwo.toEntity(), locFour, 21);

        stockService.create(stockOne);
        stockService.create(stockTwo);
        stockService.create(stockThree);
        stockService.create(stockFour);
    }

    @Test
    void testCreateOrder(@Value("${strategy.findLocation}") String strategy) throws Exception {
        StockId stockId1 = new StockId(1, 1);
        StockId stockId2 = new StockId(2, 2);
        StockId stockId3 = new StockId(1, 3);
        StockId stockId4 = new StockId(2, 4);

        int stock1Prod1Qty = stockService.readById(stockId1).getQuantity();
        int stock2Prod2Qty = stockService.readById(stockId2).getQuantity();
        int stock3Prod1Qty = stockService.readById(stockId3).getQuantity();
        int stock4Prod2Qty = stockService.readById(stockId4).getQuantity();

        int testProd1Qty = 10;
        int testProd2Qty = 20;

        ProdOrdCreatorDTO prod1Wanted = new ProdOrdCreatorDTO(1, testProd1Qty);
        ProdOrdCreatorDTO prod2Wanted = new ProdOrdCreatorDTO(2, testProd2Qty);

        List<ProdOrdCreatorDTO> listProductWanted = new ArrayList<>();
        listProductWanted.add(prod1Wanted);
        listProductWanted.add(prod2Wanted);

        OrderObjectInputDTO orderObjectInputDTO = new OrderObjectInputDTO();
        orderObjectInputDTO.setCreatedAt(LocalDateTime.of(LocalDate.of(2021, 2, 21), LocalTime.of(12, 30, 0)));
        AddressDTO deliveryAddress = addressService.listAll().get(0);
        Address delAddressInput = deliveryAddress.toEntity();
        delAddressInput.setId(1);
        orderObjectInputDTO.setDeliveryAddress(delAddressInput);
        orderObjectInputDTO.setProduct(listProductWanted);


        int initialOrderNb = orderCreatorController.listAll().size();

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(orderObjectInputDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productAsStringDTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        int actualOrderNb = orderCreatorController.listAll().size();
        Assert.assertEquals(initialOrderNb + 1, actualOrderNb);

    }
}