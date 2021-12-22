package ro.msg.learning.shop.controller.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ro.msg.learning.shop.controllers.StockExportController;
import ro.msg.learning.shop.dto.StockExportDTO;
import ro.msg.learning.shop.services.StockExportService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    /*@Test
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
    void testDeSerialization() throws Exception {
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

//        working
//        String csvText1="product,location,quantity" + "\n"+"3,1,30";
//        not working
//        String csvText2="3,1,30";

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringJSON = objectMapper.writeValueAsString(stockExportDTOListExpected);

        when(stockExportService.importStocks(any())).thenReturn(stockExportDTOListExpected);

        mvc.perform(MockMvcRequestBuilders.post("/api/stocks/export/")
                .contentType("text/csv")
                .content(expectedCsvText))
//                .accept("text/csv"))
//                not working
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(equalTo(productAsStringJSON)))
                .andReturn();

    }

}