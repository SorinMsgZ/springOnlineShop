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
import ro.msg.learning.shop.entities.ProductCategoryJdbc;
import ro.msg.learning.shop.entities.ProductJdbc;
import ro.msg.learning.shop.repositories.JdbcProductCategoryJdbcRepository;
import ro.msg.learning.shop.repositories.JdbcProductJdbcRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductAndProductCategoryJdbcRepositoryTest {

    @Autowired
    private JdbcProductJdbcRepository jdbcProductJdbcRepository;
    @Autowired
    private JdbcProductCategoryJdbcRepository jdbcProductCategoryJdbcRepository;

    @Test
    public void test1FindAllProductJdbcWhenDbHasOneElement() {

        List<ProductJdbc> productJdbcList = jdbcProductJdbcRepository.findAll();
        int productNumber = productJdbcList.size();
        Assert.assertEquals(1, productNumber);

    }

    @Test
    public void test2FindAllProductJdbcWhenOneElementIsRemovedFromDb() {
        List<ProductJdbc> productJdbcListBefore = jdbcProductJdbcRepository.findAll();
        int productNumberBefore = productJdbcListBefore.size();

        ProductJdbc productJdbc = ProductJdbc.builder()
                .id(1)
                .build();

        jdbcProductJdbcRepository.remove(productJdbc);
        List<ProductJdbc> actualProductJdbcList = jdbcProductJdbcRepository.findAll();
        int actualProductNumber = actualProductJdbcList.size();
        Assert.assertEquals(productNumberBefore-1, actualProductNumber);

    }

    @Test
    public void test3FindAllProductCategoryJdbcWhenDbHasOneElement() {

        List<ProductCategoryJdbc> productCategoryJdbcList = jdbcProductCategoryJdbcRepository.findAll();
        int productCategoryNumber = productCategoryJdbcList.size();
        Assert.assertEquals(1, productCategoryNumber);

    }

    @Test
    public void test4FindAllProductCategoryJdbcWhenOneElementIsRemovedFromDb() {
        List<ProductCategoryJdbc> productCategoryJdbcListBefore = jdbcProductCategoryJdbcRepository.findAll();
        int productCategoryNumberBefore = productCategoryJdbcListBefore.size();

        ProductCategoryJdbc productCategoryJdbc = ProductCategoryJdbc.builder()
                .id(1)
                .build();

        jdbcProductCategoryJdbcRepository.remove(productCategoryJdbc);
        List<ProductCategoryJdbc> actualProductCategoryJdbcList = jdbcProductCategoryJdbcRepository.findAll();
        int actualProductCategoryNumber = actualProductCategoryJdbcList.size();
        Assert.assertEquals(productCategoryNumberBefore-1, actualProductCategoryNumber);

    }
}
