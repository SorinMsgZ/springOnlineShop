package ro.msg.learning.shop.odata.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfiguration {
    @Bean
    public ServletRegistrationBean odataServlet(ExampleServlet servlet) {
        return new ServletRegistrationBean(servlet, "/odata/*");
    }
}
