
package ro.msg.learning.shop.controller.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.controllers.ProductController;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

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

        Assert.assertEquals(0, productService.listProduct().size());

        this.productDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);

        productService.createProduct(productDTO);
        controller = new ProductController(productService);

        Assert.assertEquals(1, productService.listProduct().size());
    }

    //@TODO: research alternative for @DirtiesContext as clearDB to be inserted as e.g. @AfterEach

    @Test
    void testListProducts() throws Exception {
        List<ProductDTO> dtoListExpected = productService.listProduct();

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(dtoListExpected);

        mvc.perform(MockMvcRequestBuilders.get("/api/products/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo(productAsStringDTO)));

    }

    @Test
    void testReadSingleProduct() throws Exception {
        int testId = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(productService.readSingleProduct(testId));

        mvc.perform(MockMvcRequestBuilders.get("/api/products/" + testId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo(productAsStringDTO)));
    }

    @Test
    void testCreateProduct() throws Exception {
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

        int initialProductNb = productService.listProduct().size();
        ProductDTO secondProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(secondProductDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productAsStringDTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));

        Assert.assertEquals(initialProductNb + 1, productService.listProduct().size());
        Assert.assertEquals(secondProductDTO, productService.readSingleProduct(id));
    }

    @Test
    void testDeleteSingleProduct() throws Exception {
        List<ProductDTO> expectedList = productService.listProduct();
        int initialProductNb = expectedList.size();
        int indexOfProduct = expectedList.indexOf(productDTO);
        int productId = productDTO.getId();

        expectedList.remove(indexOfProduct);
        Assert.assertEquals(initialProductNb - 1, expectedList.size());
        Assert.assertEquals(initialProductNb, productService.listProduct().size());

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(expectedList);

        mvc.perform(MockMvcRequestBuilders.delete("/api/products/" + productId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo("")));
        Assert.assertEquals(initialProductNb - 1, productService.listProduct().size());
    }

    @Test
    void testUpdateSingleProduct() throws Exception {
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

        int intProduct = 1;

        ProductDTO updateProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplierId, supplierName, imageUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(updateProductDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/products/" + intProduct)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productAsStringDTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));

        Assert.assertEquals(1, productService.listProduct().size());
        Assert.assertEquals(updateProductDTO.toString(), productService.readSingleProduct(id).toString());

    }
}



