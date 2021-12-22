package ro.msg.learning.shop.service.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.controllers.StockExportController;
import ro.msg.learning.shop.dto.*;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.StockRepository;
import ro.msg.learning.shop.services.CsvTranslator;
import ro.msg.learning.shop.services.CsvTranslatorDecorator;
import ro.msg.learning.shop.services.StockExportService;
import ro.msg.learning.shop.services.StockService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setMaxLengthForSingleLineDescription;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class CsvSerializationDeserializationServiceUnitTest {


    @MockBean
    private StockExportService stockExportService;

    private CsvTranslator csvTranslator;

    private final List<StockExportDTO> stockExportDTOListExpected = new ArrayList<>();
    private String xList = "";

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

        csvTranslator = new CsvTranslator<StockExportDTO>();
    }

    /*@Test
    void testSerialization() throws Exception {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(StockExportDTO.class);
        StringWriter strW = new StringWriter();
        SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
        seqW.write(stockExportDTOListExpected);
        seqW.close();

        String headerOfCsvText = "product,location,quantity" + "\n";
        String expectedCsvText = headerOfCsvText + strW.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringJSON = objectMapper.writeValueAsString(stockExportDTOListExpected);

        FileOutputStream f = new FileOutputStream("stocuri.csv");

        csvTranslator.toCsv(StockExportDTO.class, stockExportDTOListExpected, f);

        List<List<String>> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("stocuri.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                list.add(Arrays.asList(values));
            }
        }

        int n = list.size();
        for (List<String> strings : list) {
            for (int j = 0; j < n; j++) {
                xList = xList + strings.get(j);
                if (j < n - 1) {
                    xList = xList + ",";
                }
            }
            xList = xList + "\n";
        }

        Assert.assertEquals(expectedCsvText, xList);
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

        InputStream inputStream = new ByteArrayInputStream(expectedCsvText.getBytes());

        List<StockExportDTO> expectedListFromCsvToPojo = csvTranslator.fromCsv(StockExportDTO.class, inputStream);
        Assert.assertEquals(stockExportDTOListExpected, expectedListFromCsvToPojo);

    }

}
