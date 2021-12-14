package ro.msg.learning.shop.controller.unitTest;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.controllers.StockExportController;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CsvSerializationDeserializationTest {


    //    @Mock
//    private ProductCategoryRepository productCategoryRepository;
//    @Mock
//    private SupplierRepository supplierRepository;
//    @Mock
//    private ProductRepository productRepository;
//    @Mock
//    private AddressRepository addressRepository;
//    @Mock
//    private LocationRepository locationRepository;
//    @Mock
//    private OrderRepository orderRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockExportService stockExportService;

    @InjectMocks
    private StockExportController stockExportController;
    @InjectMocks
    CsvTranslatorDecorator csvTranslatorDecorator;

    private List<StockExportDTO> stockExportDTOListExpected = new ArrayList<>();

    @BeforeEach
    void createProductDTO() {
        ProductCategoryDTO productCategoryOne =
                new ProductCategoryDTO("Retaining Wall and Brick Pavers", "ProdCatDescription1");
        ProductCategoryDTO productCategoryTwo =
                new ProductCategoryDTO("Drywall & Acoustical (MOB)", "ProdCatDescription2");
//        productCategoryService.create(productCategoryOne);
//        productCategoryService.create(productCategoryTwo);

        SupplierDTO supplierOne = new SupplierDTO("Fisher-Huels");
        SupplierDTO supplierTwo = new SupplierDTO("D Amore, Torp and Kuvalis");
//        supplierService.create(supplierOne);
//        supplierService.create(supplierTwo);

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
        sup2.setId(2);
        productTwo.setSupplier(sup2);
        productTwo.setImageUrl("http://22dummyimage.com/104x100.png/ff4444/ffffff");

        ProductDTO productThree = new ProductDTO();
        productThree.setId(3);
        productThree.setName("Three Name");
        productThree.setDescription("Three pulvinar");
        productThree.setPrice(new BigDecimal("34"));
        productThree.setWeight(300);
        productThree.setProductCategoryId(2);
        productThree.setProductCategoryName("Drywall & Acoustical (MOB)");
        productThree.setProductCategoryDescription("ProdCatDescription2");
        Supplier sup3 = supplierTwo.toEntity();
        sup3.setId(3);
        productTwo.setSupplier(sup3);
        productTwo.setImageUrl("http://33dummyimage.com/104x100.png/ff4444/333");

//        productService.create(productOne);
//        productService.create(productTwo);
//        productService.create(productThree);

        AddressDTO deliveryAddress = new AddressDTO("United States", "Rochester", "New York", "440 Merry Drive");
//        addressService.create(deliveryAddress);

        Address delAddress = deliveryAddress.toEntity();
        delAddress.setId(1);


        LocationDTO locationOne = new LocationDTO("cbslocal.com", delAddress);
        LocationDTO locationTwo = new LocationDTO("msn.com", delAddress);
        LocationDTO locationThree = new LocationDTO("sdgsg", delAddress);
        LocationDTO locationFour = new LocationDTO("jdjtzk", delAddress);

//        locationService.create(locationOne);
//        locationService.create(locationTwo);
//        locationService.create(locationThree);
//        locationService.create(locationFour);

        Location locOne = locationOne.toEntity();
        locOne.setId(1);
        Location locTwo = locationTwo.toEntity();
        locTwo.setId(2);
        Location locThree = locationThree.toEntity();
        locThree.setId(3);
        Location locFour = locationFour.toEntity();
        locFour.setId(4);

        StockDTO stockOne = new StockDTO(productOne.toEntity(), locOne, 10);
        StockDTO stockTwo = new StockDTO(productTwo.toEntity(), locOne, 20);
        StockDTO stockThree = new StockDTO(productThree.toEntity(), locOne, 11);
        StockDTO stockFour = new StockDTO(productTwo.toEntity(), locFour, 21);

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
