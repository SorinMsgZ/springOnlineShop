package ro.msg.learning.shop.services;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

@Component

@RequiredArgsConstructor
public class CsvTranslatorDecorator extends AbstractGenericHttpMessageConverter {

    public final CsvTranslator csvTranslator;

    @Override
    protected void writeInternal(Object o, Type type,
                                 HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        //aici practic doar se apeleaza metoda toCsv() din clasa mea utila "CsvTranslator"=>
        // trebuie corelati parametrii din metoda "writeInternal()" cu cei din metoda toCsv();
//       parametrul "Object o" - este fie un singur obiect tip StockDTO fie o lista de StockDTO
//        => Object o -   trebuie cast-uit to List
//        outputMessage => va fi Output stream
//        CsvTranslatorDecorator => va fi un bean (=> @Component)
        if ((type instanceof Class<?>)&&(o instanceof List<?>)) {
            csvTranslator.toCsv((Class) type, (List<?>) o, (OutputStream) outputMessage);
        }
    }

    @Override
    protected Object readInternal(Class clazz,
                                  HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//aici practic doar se apeleaza metoda fromCsv() din clasa mea utila "CsvTranslator"=> trebuie corelati parametrii din metoda "readInternal()" cu cei din metoda toCsv();


        return csvTranslator.fromCsv(clazz, (InputStream) inputMessage);
    }

    @Override
    public Object read(Type type, Class contextClass,
                       HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//se apeleaza metoda readInternal();

        return readInternal((Class) type, inputMessage);
    }
}
