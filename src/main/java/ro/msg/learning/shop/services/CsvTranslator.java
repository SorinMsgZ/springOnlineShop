
package ro.msg.learning.shop.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component

public final class CsvTranslator<T> {

    private final CsvMapper mapper = new CsvMapper();

    public List<T> fromCsv(Class<T> type, InputStream inputCsvFile) throws IOException {

        CsvSchema schema = mapper.schemaFor(type).withHeader();
        ObjectReader oReader = mapper.readerFor(type).with(schema);
        MappingIterator<T> it = oReader.readValues(inputCsvFile);
        return it.readAll();
    }

    public void toCsv(Class<T> type, List<T> list,
                      OutputStream outputCsvFile) throws IOException {

        try {
            CsvSchema schema = mapper.schemaFor(type).withHeader();
            ObjectWriter writer = mapper.writer(schema.withLineSeparator("\n"));
            writer.writeValue(outputCsvFile, list);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

    }
}

