/*
package ro.msg.learning.shop.controller.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.entities.Product;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:test.properties")
public class CreateProductIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    */
/*
    public void createProduct() {
        ResponseEntity<Product> responseEntity =
                restTemplate.postForEntity("/api/products", new CreateProductRequest("Test"), Product.class);
        Product product = responseEntity.getBody();
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertEquals("Test", product.getName());}
       *//*


    public void getProducts() throws Exception {
       Product product = new Product();
        int productId= product.getId();


        ResponseEntity<Product> responseEntity =
                restTemplate.getForEntity("/api/products/1", Product.class);
        assertThat(responseEntity.getBody()).isEqualTo(product);

    }

}
*/
