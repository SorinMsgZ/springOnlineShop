package ro.msg.learning.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ro.msg.learning.shop.services.CsvTranslator;
import ro.msg.learning.shop.services.CsvTranslatorDecorator;

import java.util.List;

@EnableWebMvc
@Service
@Configuration
//@ComponentScan({""})
@RequiredArgsConstructor

/*public class WebConfigurer implements WebMvcConfigurer {
    public final CsvTranslator csvTranslator;

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CsvTranslatorDecorator(csvTranslator));
    }
}*/
public class WebConfig implements WebMvcConfigurer {
    public final CsvTranslator csvTranslator;
    public final CsvTranslatorDecorator csvTranslatorDecorator;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(createCsvHttpMessageConverter());
        messageConverters.add(new CsvTranslatorDecorator(csvTranslator));
    }

    @Bean
    public HttpMessageConverter<Object> createCsvHttpMessageConverter() {
//        CsvTranslatorDecorator csvTranslatorDecorator = new CsvTranslatorDecorator(csvTranslator);
        return csvTranslatorDecorator;
    }
}