/*
package ro.msg.learning.shop.controller.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.SupplierRepository;
import ro.msg.learning.shop.services.ProductService;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ProductControllerTestWebMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    ProductService createProductServiceMock;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void testCreateProductSuccessfully() throws Exception {
        //given(createProductServiceMock.createProduct("Test")).willReturn(new Product("Test"));
        mvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes((new CreateProductRequest("Test"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.number", notNullValue())));
    }


}


*/
