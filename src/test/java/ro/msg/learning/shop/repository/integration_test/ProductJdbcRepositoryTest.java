package ro.msg.learning.shop.repository.integration_test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.entities.ProductJdbc;
import ro.msg.learning.shop.repositories.JdbcProductJdbcRepository;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class ProductJdbcRepositoryTest {

    @Autowired
    private JdbcProductJdbcRepository jdbcProductJdbcRepository;

    @Test
    public void testFindAll() {

        List<ProductJdbc> productJdbcList =jdbcProductJdbcRepository.findAll();
        int productNumber = productJdbcList.size();
        Assert.assertEquals(1, productNumber);

    }
}
