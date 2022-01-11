package ro.msg.learning.shop.controller.integration_test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.controllers.StockExportController;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.services.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest

@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StockExportControllerTest {
    @Autowired
    private StockExportController stockExportController;
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
    List<StockExportDTO> stockExportDTOListExpected = new ArrayList<>();

    @BeforeEach
    void createProductDTO() {

        ProductCategoryDTO productCategoryOne = ProductCategoryDTO.builder()
                .name("Retaining Wall and Brick Pavers")
                .description("ProdCatDescription1")
                .build();

        ProductCategoryDTO productCategoryTwo = ProductCategoryDTO.builder()
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

        ProductDTO productOne = ProductDTO.builder()
                .id(1)
                .name("Fimbristylis vahlii (Lam.) Link")
                .description("quis orci")
                .price(new BigDecimal("7.4"))
                .weight(100)
                .productCategoryId(1)
                .productCategoryName("Retaining Wall and Brick Pavers")
                .productCategoryDescription("ProdCatDescription1")
                .imageUrl("http://11dummyimage.com/223x100.png/ff4444/ffffff")
                .build();

        ProductDTO productTwo = ProductDTO.builder()
                .id(2)
                .name("Mahonia Nutt., pulvinar")
                .description("pulvinar")
                .price(new BigDecimal("21.96"))
                .weight(200.20)
                .productCategoryId(2)
                .productCategoryName("Drywall & Acoustical (MOB)")
                .productCategoryDescription("ProdCatDescription2")
                .imageUrl("http://22dummyimage.com/104x100.png/ff4444/ffffff")
                .build();

        ProductDTO productThree = ProductDTO.builder()
                .id(3)
                .name("Three Name")
                .description("Three pulvinar")
                .price(new BigDecimal("34"))
                .weight(300)
                .productCategoryId(2)
                .productCategoryName("Drywall & Acoustical (MOB)")
                .productCategoryDescription("ProdCatDescription2")
                .imageUrl("http://33dummyimage.com/104x100.png/ff4444/333")
                .build();

        productService.create(productOne);
        productService.create(productTwo);
        productService.create(productThree);

        AddressDTO deliveryAddress = AddressDTO.builder()
                .country("United States")
                .city("Rochester")
                .county("New York")
                .streetAddress("440 Merry Drive")
                .build();

        addressService.create(deliveryAddress);

        Address delAddress = deliveryAddress.toEntity();
        delAddress.setId(1);

        LocationDTO locationOne = LocationDTO.builder()
                .name("cbslocal.com")
                .address(delAddress)
                .build();

        LocationDTO locationTwo = LocationDTO.builder()
                .name("msn.com")
                .address(delAddress)
                .build();

        LocationDTO locationThree = LocationDTO.builder()
                .name("sdgsg")
                .address(delAddress)
                .build();

        LocationDTO locationFour = LocationDTO.builder()
                .name("jdjtzk")
                .address(delAddress)
                .build();

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

        StockDTO stockOne = StockDTO.builder()
                .product(productOne.toEntity())
                .location(locOne)
                .quantity(10)
                .build();

        StockDTO stockTwo = StockDTO.builder()
                .product(productTwo.toEntity())
                .location(locOne)
                .quantity(20)
                .build();

        StockDTO stockThree = StockDTO.builder()
                .product(productThree.toEntity())
                .location(locOne)
                .quantity(11)
                .build();

        StockDTO stockFour = StockDTO.builder()
                .product(productTwo.toEntity())
                .location(locFour)
                .quantity(21)
                .build();

        stockService.create(stockOne);
        stockService.create(stockTwo);
        stockService.create(stockThree);
        stockService.create(stockFour);

        StockExportDTO stockExport1 = StockExportDTO.of(stockOne.toEntity());
        StockExportDTO stockExport2 = StockExportDTO.of(stockTwo.toEntity());
        StockExportDTO stockExport3 = StockExportDTO.of(stockThree.toEntity());

        stockExportDTOListExpected.add(stockExport1);
        stockExportDTOListExpected.add(stockExport2);
        stockExportDTOListExpected.add(stockExport3);
    }

    @Test
    void testExporting() {

        int locationId = 1;

        HttpServletResponse httpServletResponse = new MockHttpServletResponse();

        List<StockExportDTO> stockDTOListActual =
                stockExportController.exportingStockByLocationId(locationId, httpServletResponse);
        Assert.assertEquals(stockExportDTOListExpected.size(), stockDTOListActual.size());
        Assert.assertEquals(stockExportDTOListExpected, stockDTOListActual);
    }
}
