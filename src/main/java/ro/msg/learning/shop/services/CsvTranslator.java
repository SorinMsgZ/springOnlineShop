
package ro.msg.learning.shop.services;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entities.Stock;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Component

public final class CsvTranslator <T> {

    private final CsvMapper mapper = new CsvMapper();

//    private CsvTranslator() {
//        throw new UnsupportedOperationException();
//    }


    //readData =>deserializare => CsvMapper=> ObjectReader
    //ObjectInputStream
    public <T> List<T> fromCsv(Class<T> type, InputStream inputCsvFile) throws IOException {


/* T value = (T) inputCsvFile;
        CsvSchema schema = mapper.schemaFor(type);
        String csv = mapper.writer(schema).writeValueAsString(value);
        MappingIterator<T> it = mapper.readerFor(type).with(schema).readValues(csv);
        return it.readAll();*/


        CsvSchema schema = mapper.schemaFor(type).withHeader();
        ObjectReader oReader = mapper.readerFor(type).with(schema);
        MappingIterator<T> it = oReader.readValues(inputCsvFile);
        return it.readAll();
    }

    //write data=>serializare => CsvMapper => ObjectWriter
    //ObjectOutputStream
    //StringWriter= A character stream that collects its output in a string buffer,
    //...which can then be used to construct a string.
    //SequenceWriter= Writer class similar to {@link ObjectWriter}, except that it
    //...can be used for writing sequences of values, not just a single value.
    public <T> void toCsv(Class<T> type, List<T> list,
                          OutputStream outputCsvFile) throws IOException {

/*CsvSchema schema = mapper.schemaFor(type);
        try (StringWriter strW = new StringWriter()) {

            SequenceWriter seqW = mapper.writer(schema).writeValues(strW);
            seqW.write(Arrays.asList(list));
            seqW.close();
          strW.toString();*/

        try {
            CsvSchema schema = mapper.schemaFor(type).withHeader();
            ObjectWriter writer = mapper.writer(schema.withLineSeparator("\n"));
            writer.writeValue(outputCsvFile,list);
        } catch (Exception e) {
        }

    }
}

