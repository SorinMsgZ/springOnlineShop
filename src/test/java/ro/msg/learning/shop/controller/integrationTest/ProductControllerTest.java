
package ro.msg.learning.shop.controller.integrationTest;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.controllers.ProductController;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.services.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
class ProductControllerTest {

    @Autowired
    private ProductService productService;

    private ProductDTO productDTO;
    private ProductController controller;

    @BeforeEach
    public void createProductDTO() {
        int id = 1;
        String name = "1TestNameDTO";
        String description = "1 Test Product Description";
        BigDecimal price = new BigDecimal("10.10");
        double weight = 100;
        int productCategoryId = 1;
        String productCategoryName = "1TestNameProdCat";
        String productCategoryDescription = "1 Test Product Category Description";
        int supplierId = 1;
        String supplierName = "1TestNameSupplier";
        String imageUrl = "www.1TestUrl.com";

        Assert.assertEquals(0,productService.listProduct().size());

        this.productDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);

        productService.createProduct(productDTO);
        controller = new ProductController(productService);

        Assert.assertEquals(1, productService.listProduct().size());
    }

    @Test
    void testListProducts()  {
        List<ProductDTO> dtoListExpected = productService.listProduct();
        List<ProductDTO> dtoListActual = controller.listAllProducts();
        Assert.assertEquals(dtoListExpected.size(), dtoListActual.size());
    }

    @Test
    void testReadSingleProduct(){
        int productId = 1;
        ProductDTO actualProductDTO = controller.readSingleProduct(productId);
        Assert.assertEquals(productDTO.toString(), actualProductDTO.toString());
    }

    @Test
    void testCreateProduct()  {
        int id = 2;
        String name = "2TestNameSecondDTO";
        String description = "2Test Product Description";
        BigDecimal price = new BigDecimal("20.20");
        double weight = 200;
        int productCategoryId = 2;
        String productCategoryName = "2TestNameProdCat";
        String productCategoryDescription = "2Test Product Category Description";
        int supplierId = 2;
        String supplierName = "2TestNameSupplier";
        String imageUrl = "www.2TestUrl.com";

        int initialProductNb= productService.listProduct().size();
        ProductDTO secondProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);

        controller.createProduct(secondProductDTO);

        Assert.assertEquals(initialProductNb+1, productService.listProduct().size());
        Assert.assertEquals(secondProductDTO, productService.readSingleProduct(id));

    }

    @Test
    void testDeleteSingleProduct() {
        int initialProductNb= productService.listProduct().size();
        int productId = 1;
        controller.deleteSingleProduct(productId);
        Assert.assertEquals(initialProductNb-1, productService.listProduct().size());
    }

    @Test
    void testUpdateSingleProduct()  {
        int id = 1;
        String name = "3TestNameSecondDTO";
        String description = "3Test Product Description";
        BigDecimal price = new BigDecimal("30.30");
        double weight = 300;
        int productCategoryId = 1;
        String productCategoryName = "3TestNameProdCat";
        String productCategoryDescription = "3Test Product Category Description";
        int supplierId = 1;
        String supplierName = "3TestNameSupplier";
        String imageUrl = "www.3TestUrl.com";

        ProductDTO updateProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);
        controller.updateSingleProduct(1, updateProductDTO);

        Assert.assertEquals(1, productService.listProduct().size());
        Assert.assertEquals(updateProductDTO.toString(), productService.readSingleProduct(id).toString());

    }
}



