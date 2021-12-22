package ro.msg.learning.shop.controller.unitTest;

import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
import ro.msg.learning.shop.services.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CsvSerializationDeserializationTest {


    @Mock
    private StockExportService stockExportService;
    @InjectMocks
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
        HttpServletResponse response = new MockHttpServletResponse();

        int locationId = 1;
        when(stockExportService.exportingStockByLocationId(locationId)).thenReturn(stockExportDTOListExpected);

        List<StockExportDTO> actualResponse = stockExportController.exportingStockByLocationId(locationId, response);

        Assert.assertEquals(stockExportDTOListExpected, actualResponse);
    }

  /*  @Test
    void testDeSerialization() throws IOException {
        StockExportDTO stockInput = StockExportDTO.builder()
                .productId(3)
                .locationId(1)
                .quantity(30)
                .build();
        stockExportDTOListExpected.add(stockInput);
        when(stockExportService.createStocks(any())).thenReturn(stockExportDTOListExpected);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(StockExportDTO.class);
        StringWriter strW = new StringWriter();
        SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
        seqW.write(stockExportDTOListExpected);
        seqW.close();

        String headerOfCsvText = "product,location,quantity" + "\n";
        String expectedCsvText = headerOfCsvText + strW.toString();

//        String textAsCsv="product,location,quantity" + "\n"+"3,1,30";

        FileOutputStream fileStream = new FileOutputStream("textCsv.csv");
        ObjectOutputStream os = new ObjectOutputStream(fileStream);
        os.writeObject(expectedCsvText);
        os.close();

        FileInputStream fileInputStream = new FileInputStream("textCsv.csv");
        ObjectInputStream ois = new ObjectInputStream(fileInputStream);
        ois.close();
        List<StockExportDTO> stockActualResponse = stockExportController.createStocks(ois);

        Assert.assertEquals(stockExportDTOListExpected, stockActualResponse);
    }*/

}
