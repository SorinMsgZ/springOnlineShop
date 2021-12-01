package ro.msg.learning.shop.repository.integrationTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByProductName() {

        ProductCategory prodCat = new ProductCategory();
        prodCat.setId(24);
        prodCat.setName("sddgsg");
        prodCat.setDescription("SffF");

        Supplier supplier = new Supplier();
        supplier.setId(24);
        supplier.setName("sdgsg");

        Product prod = new Product();
        prod.setId(33);
        prod.setName("ProductNameTest");
        prod.setDescription("disgusts");
        prod.setPrice(new BigDecimal("0.01"));
        prod.setWeight(3535);
        prod.setCategory(prodCat);
        prod.setSupplier(supplier);
        prod.setImageUrl("serfs");
        entityManager.persist(prod);

        Optional<Product> product = productRepository.findByName("ProductNameTest").stream()
                .filter(p -> p.getName().equals("ProductNameTest")).findFirst();
        Assert.assertEquals("ProductNameTest", product.get().getName());


    }
}
