package ro.msg.learning.shop.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ThymeleafEncoder {

    @Bean
    public BCryptPasswordEncoder encoder() {
        int strength = 12;
        return new BCryptPasswordEncoder(strength);
    }
}
