package ro.msg.learning.shop.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@Profile("mongodb")
@Configuration
@EnableMongoRepositories(basePackages = "ro.msg.learning.shop.repositories")

public class SimpleMongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String connection;

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(connection);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);


    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }
}
