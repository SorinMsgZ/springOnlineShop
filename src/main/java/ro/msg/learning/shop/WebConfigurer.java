package ro.msg.learning.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ro.msg.learning.shop.services.CsvTranslator;
import ro.msg.learning.shop.services.CsvTranslatorDecorator;

import java.util.List;
@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements WebMvcConfigurer {
    public final CsvTranslator csvTranslator;

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CsvTranslatorDecorator(csvTranslator));
    }
}
