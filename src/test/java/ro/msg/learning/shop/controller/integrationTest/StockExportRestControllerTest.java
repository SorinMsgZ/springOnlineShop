package ro.msg.learning.shop.controller.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.controllers.*;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.Address;
import ro.msg.learning.shop.entities.Location;

import ro.msg.learning.shop.services.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        ProductCategoryDTO productCategoryOne = ProductCategoryDTO.builder()
                .name("Retaining Wall and Brick Pavers")
                .description("ProdCatDescription1")
                .build();

        ProductCategoryDTO productCategoryTwo = ProductCategoryDTO.builder()
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

        productController.create(productOne);
        productController.create(productTwo);
        productController.create(productThree);

        AddressDTO deliveryAddress = AddressDTO.builder()
                .country("United States")
                .city("Rochester")
                .county("New York")
                .streetAddress("440 Merry Drive")
                .build();

        addressController.create(deliveryAddress);

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

   /* @Test
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

    }*/

    @Test
    void testImportStocks() throws Exception {
        StockExportDTO stockInput = StockExportDTO.builder()
                .productId(3)
                .locationId(1)
                .quantity(30)
                .build();

        stockExportDTOListExpected.add(stockInput);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(StockExportDTO.class);
        StringWriter strW = new StringWriter();
        SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
        seqW.write(stockExportDTOListExpected);
        seqW.close();

        String headerOfCsvText = "product,location,quantity" + "\n";
        String expectedCsvText = headerOfCsvText + strW.toString();

        InputStream inputStream=new ByteArrayInputStream((expectedCsvText.getBytes()));

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringJSON = objectMapper.writeValueAsString(stockExportDTOListExpected);

        mvc.perform(MockMvcRequestBuilders.post("/api/stocks/export/")
                .contentType("text/csv")
                .content(inputStream.readAllBytes())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(productAsStringJSON)))
                .andReturn();

    }
}
