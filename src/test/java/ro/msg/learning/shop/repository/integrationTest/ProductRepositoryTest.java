package ro.msg.learning.shop.repository.integrationTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testFindByProductId() {

        ProductCategory prodCat = new ProductCategory();
        prodCat.setName("NameOfProdCat");
        prodCat.setDescription("Description on Prd Cat");

        Supplier supplier = new Supplier();
        supplier.setName("SupplierNmae");

        Product prodTest = new Product();

        String nameOfProduct = "nameOfProduct";
        prodTest.setName(nameOfProduct);
        prodTest.setDescription("disgusts");
        prodTest.setPrice(new BigDecimal("0.01"));
        prodTest.setWeight(3535);
        prodTest.setCategory(prodCat);
        prodTest.setSupplier(supplier);
        prodTest.setImageUrl("serfs");

        productRepository.save(prodTest);

        Optional<Product> product = productRepository.findById(1).stream().findFirst();

        assert product.orElse(null) != null;
        Assert.assertEquals("nameOfProduct", product.orElse(null).getName());

    }
}
