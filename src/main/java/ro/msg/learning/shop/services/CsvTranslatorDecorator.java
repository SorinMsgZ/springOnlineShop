package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvTranslatorDecorator extends AbstractGenericHttpMessageConverter {

    public final CsvTranslator csvTranslator;

    public CsvTranslatorDecorator() {
        super(new MediaType("text", "csv"));
        this.csvTranslator = new CsvTranslator<>();
    }

    @Override
    protected void writeInternal(Object o, Type type,
                                 HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        List<Object> listO;
        if (o instanceof List) {
            listO = new ArrayList<>((ArrayList<Object>) o);
        } else {
            listO = Collections.singletonList(o);
        }
        if (!listO.isEmpty()) {
            csvTranslator.toCsv(listO.get(0).getClass(), listO, outputMessage.getBody());
        }
    }

    @Override
    protected Object readInternal(Class clazz,
                                  HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return csvTranslator.fromCsv(clazz, inputMessage.getBody());
    }


    @Override
    public Object read(Type type, Class contextClass,
                       HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return readInternal(contextClass, inputMessage);
    }
}
