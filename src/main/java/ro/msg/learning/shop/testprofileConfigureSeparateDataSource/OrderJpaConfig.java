package ro.msg.learning.shop.testprofileConfigureSeparateDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.repositories")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class OrderJpaConfig {
    @Autowired
    private Environment env;

    @Bean
    @Profile("TestProfile1")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.driverClassName")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        return dataSource;
    }
}
