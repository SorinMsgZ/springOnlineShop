package ro.msg.learning.shop.repository.integration_test;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.entities.ProductMongoDb;
import ro.msg.learning.shop.repositories.ProductMongoDbRepository;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductMongoDbRepositoryTest {

    @Autowired
    private ProductMongoDbRepository productMongoDbRepository;

    @Test
    public void test1SaveStoreOneProductMongoDb() {

        ProductMongoDb productMongoDb = ProductMongoDb.builder()
                .id(1)
                .name("nameOfProduct")
                .description("disgusts")
                .price(new BigDecimal("0.01"))
                .weight(3535)
                .category("prodCatString")
                .supplier("supplierString")
                .imageUrl("serfs")
                .build();

        productMongoDbRepository.save(productMongoDb);
        Optional<ProductMongoDb> optionalProductMongoDb = productMongoDbRepository.findById(1).stream().findFirst();

        assert optionalProductMongoDb.orElse(null) != null;
        Assert.assertEquals("nameOfProduct", optionalProductMongoDb.orElse(null).getName());

    }

    @Test
    public void test2RemoveAllProductMongoDb() {

        productMongoDbRepository.deleteAll();
        int actualNumberOfProducts = productMongoDbRepository.findAll().size();

        Assert.assertEquals(0, actualNumberOfProducts);

    }
}
