package ro.msg.learning.shop.controller.integrationTest;

import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.controllers.*;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.services.*;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StockExportRestControllerTest {
    @Autowired
    private MockMvc mvc;
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
    CsvTranslatorDecorator csvTranslatorDecorator;
    @Autowired
    CsvTranslator<StockExportDTO> csvTranslator;
    @Autowired
    StockExportController stockExportController;
    List<StockExportDTO> stockExportDTOListExpected = new ArrayList<>();

    @BeforeEach
    void createDataBaseForTest() {
        ProductCategoryDTO productCategoryOne =
                new ProductCategoryDTO("Retaining Wall and Brick Pavers", "ProdCatDescription1");
        ProductCategoryDTO productCategoryTwo =
                new ProductCategoryDTO("Drywall & Acoustical (MOB)", "ProdCatDescription2");
        productCategoryController.create(productCategoryOne);
        productCategoryController.create(productCategoryTwo);

        SupplierDTO supplierOne = new SupplierDTO("Fisher-Huels");
        SupplierDTO supplierTwo = new SupplierDTO("D Amore, Torp and Kuvalis");
        supplierController.create(supplierOne);
        supplierController.create(supplierTwo);

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

        productController.create(productOne);
        productController.create(productTwo);
        productController.create(productThree);

        AddressDTO deliveryAddress = new AddressDTO("United States", "Rochester", "New York", "440 Merry Drive");
        addressController.create(deliveryAddress);

        Address delAddress = deliveryAddress.toEntity();
        delAddress.setId(1);


        LocationDTO locationOne = new LocationDTO("cbslocal.com", delAddress);
        LocationDTO locationTwo = new LocationDTO("msn.com", delAddress);
        LocationDTO locationThree = new LocationDTO("sdgsg", delAddress);
        LocationDTO locationFour = new LocationDTO("jdjtzk", delAddress);

        locationController.create(locationOne);
        locationController.create(locationTwo);
        locationController.create(locationThree);
        locationController.create(locationFour);

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

        stockController.create(stockOne);
        stockController.create(stockTwo);
        stockController.create(stockThree);
        stockController.create(stockFour);

        StockExportDTO stockExport1 = StockExportDTO.of(stockOne.toEntity());
        StockExportDTO stockExport2 = StockExportDTO.of(stockTwo.toEntity());
        StockExportDTO stockExport3 = StockExportDTO.of(stockThree.toEntity());

        stockExportDTOListExpected.add(stockExport1);
        stockExportDTOListExpected.add(stockExport2);
        stockExportDTOListExpected.add(stockExport3);
    }

    @Test
    void testExportingStockByLocationId() throws Exception {

        int locationId = 1;

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(StockExportDTO.class);
        StringWriter strW = new StringWriter();
        SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
        seqW.write(stockExportDTOListExpected);
        seqW.close();

        String headerOfCsvText = "product,location,quantity" + "\n";
        String expectedCsvText = headerOfCsvText + strW.toString();

        mvc.perform(MockMvcRequestBuilders.get("/api/stocks/export/" + locationId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(MockMvcResultMatchers.content().string(expectedCsvText))
                .andExpect(content()
                        .string(equalTo(expectedCsvText)))
                .andReturn();

    }
}
