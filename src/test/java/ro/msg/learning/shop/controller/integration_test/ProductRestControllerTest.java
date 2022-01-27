
package ro.msg.learning.shop.controller.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ro.msg.learning.shop.controllers.ProductController;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:test.properties")
@ActiveProfiles("with-base")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductService productService;

    ProductController productController;

    private ProductDTO productDTO;

    @Before
    public void mockOneProductDTO() {
        productController = new ProductController(productService);
        Assert.assertEquals(0, productController.listAll().size());

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

        productController.create(productDTO);

        Assert.assertEquals(1, productController.listAll().size());
    }

    @Test
    public void testListAll() throws Exception {
        List<ProductDTO> dtoListExpected = productController.listAll();

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(dtoListExpected);

        mvc.perform(MockMvcRequestBuilders.get("/api/products/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo(productAsStringDTO)));

    }

    @Test
    public void testReadById() throws Exception {
        int testId = 1;
        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(productController.readById(testId));

        mvc.perform(MockMvcRequestBuilders.get("/api/products/" + testId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo(productAsStringDTO)));
    }

    @Test
    public void testCreate() throws Exception {
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

        int initialProductNb = productController.listAll().size();
        ProductDTO secondProductDTO =
                new ProductDTO(id, name, description, price, weight, productCategoryId, productCategoryName, productCategoryDescription, supplier2, imageUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(secondProductDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productAsStringDTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));

        Assert.assertEquals(initialProductNb + 1, productController.listAll().size());
        Assert.assertEquals(secondProductDTO, productController.readById(id));
    }

    @Test
    public void testDeleteById() throws Exception {
        List<ProductDTO> expectedList = productController.listAll();
        int initialProductNb = expectedList.size();
        int indexOfProduct = expectedList.indexOf(productDTO);
        int productId = productDTO.getId();

        expectedList.remove(indexOfProduct);
        Assert.assertEquals(initialProductNb - 1, expectedList.size());
        Assert.assertEquals(initialProductNb, productController.listAll().size());

        mvc.perform(MockMvcRequestBuilders.delete("/api/products/" + productId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(equalTo("")));
        Assert.assertEquals(initialProductNb - 1, productController.listAll().size());
    }

    @Test
    public void testUpdateById() throws Exception {

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

        ObjectMapper objectMapper = new ObjectMapper();
        String productAsStringDTO = objectMapper.writeValueAsString(updateProductDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/products/" + id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productAsStringDTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));

        Assert.assertEquals(1, productController.listAll().size());
        Assert.assertEquals(updateProductDTO.toString(), productController.readById(id).toString());

    }
}



