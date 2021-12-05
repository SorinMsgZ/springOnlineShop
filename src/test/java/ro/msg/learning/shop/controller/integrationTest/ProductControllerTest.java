
package ro.msg.learning.shop.controller.integrationTest;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.controllers.ProductController;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerTest {

    @Autowired
    private ProductService productService;

    private ProductDTO productDTO;
    private ProductController controller;

    @BeforeEach
    public void createProductDTO() {

        Assert.assertEquals(0, productService.listAll().size());

        int id = 1;
        String name = "1TestNameDTO";
        String description = "1 Test Product Description";
        BigDecimal price = new BigDecimal("10.10");
        double weight = 100;
        int productCategoryId = 1;
        String productCategoryName = "1TestNameProdCat";
        String productCategoryDescription = "1 Test Product Category Description";
        Supplier supplier1 = new Supplier();
        supplier1.setId(1);
        supplier1.setName("1TestNameSupplier");
        String imageUrl = "www.1TestUrl.com";

        this.productDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplier1, imageUrl);

        productService.create(productDTO);
        Assert.assertEquals(1, productService.listAll().size());

        controller = new ProductController(productService);
    }

    @Test
    void testListProducts() {
        List<ProductDTO> dtoListExpected = productService.listAll();
        List<ProductDTO> dtoListActual = controller.listAll();
        Assert.assertEquals(dtoListExpected.size(), dtoListActual.size());
    }

    @Test
    void testReadSingleProduct() {
        int productId = 1;
        ProductDTO actualProductDTO = controller.readById(productId);
        Assert.assertEquals(productDTO.toString(), actualProductDTO.toString());
    }

    @Test
    void testCreateProduct() {
        int id = 2;
        String name = "2TestNameSecondDTO";
        String description = "2Test Product Description";
        BigDecimal price = new BigDecimal("20.20");
        double weight = 200;
        int productCategoryId = 2;
        String productCategoryName = "2TestNameProdCat";
        String productCategoryDescription = "2Test Product Category Description";
        Supplier supplier2 = new Supplier();
        supplier2.setId(2);
        supplier2.setName("2TestNameSupplier");
        String imageUrl = "www.2TestUrl.com";

        int initialProductNb = productService.listAll().size();
        ProductDTO secondProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplier2, imageUrl);

        controller.create(secondProductDTO);

        Assert.assertEquals(initialProductNb + 1, productService.listAll().size());
        Assert.assertEquals(secondProductDTO, productService.readById(id));

    }

    @Test
    void testDeleteSingleProduct() {
        int initialProductNb = productService.listAll().size();
        int productId = 1;
        controller.deleteById(productId);
        Assert.assertEquals(initialProductNb - 1, productService.listAll().size());
    }

    @Test
    void testUpdateSingleProduct() {
        int id = 1;
        String name = "3TestNameSecondDTO";
        String description = "3Test Product Description";
        BigDecimal price = new BigDecimal("30.30");
        double weight = 300;
        int productCategoryId = 1;
        String productCategoryName = "3TestNameProdCat";
        String productCategoryDescription = "3Test Product Category Description";
        Supplier supplier = new Supplier();
        supplier.setId(2);
        supplier.setName("3TestNameSupplier");
        String imageUrl = "www.3TestUrl.com";

        ProductDTO updateProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplier, imageUrl);
        controller.updateById(1, updateProductDTO);

        Assert.assertEquals(1, productService.listAll().size());
        Assert.assertEquals(updateProductDTO.toString(), productService.readById(id).toString());

    }
}



