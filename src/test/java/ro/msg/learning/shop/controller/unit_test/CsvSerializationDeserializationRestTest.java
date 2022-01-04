package ro.msg.learning.shop.controller.unit_test;

import com.fasterxml.jackson.databind.SequenceWriter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ro.msg.learning.shop.controllers.StockExportController;
import ro.msg.learning.shop.dto.StockExportDTO;
import ro.msg.learning.shop.services.StockExportService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(controllers = StockExportController.class)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CsvSerializationDeserializationRestTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockExportService stockExportService;

    private StockExportController stockExportController;

    private final List<StockExportDTO> stockExportDTOListExpected = new ArrayList<>();

    @BeforeEach
    void createProductDTO() {

        StockExportDTO stockOne = StockExportDTO.builder()
                .productId(1)
                .locationId(1)
                .quantity(10)
                .build();
        StockExportDTO stockTwo = StockExportDTO.builder()
                .productId(2)
                .locationId(1)
                .quantity(20)
                .build();

        stockExportDTOListExpected.add(stockOne);
        stockExportDTOListExpected.add(stockTwo);
    }

    @Test
    void testSerialization() throws Exception {
        int locationId = 1;

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(StockExportDTO.class);
        StringWriter strW = new StringWriter();
        SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
        seqW.write(stockExportDTOListExpected);
        seqW.close();

        String headerOfCsvText = "product,location,quantity" + "\n";
        String expectedCsvText = headerOfCsvText + strW.toString();

        when(stockExportService.exportingStockByLocationId(locationId)).thenReturn(stockExportDTOListExpected);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/api/stocks/export/" + locationId)
                .accept("text/csv"))
                .andReturn();

        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertEquals(expectedCsvText, result.getResponse().getContentAsString());
    }

}