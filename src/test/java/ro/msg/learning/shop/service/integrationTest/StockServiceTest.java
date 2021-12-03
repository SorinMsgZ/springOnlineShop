package ro.msg.learning.shop.service.integrationTest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.StockService;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
 class StockServiceTest {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StockService stockService;


    @Test
     void testUpdateStock() {

        Stock stock1 = new Stock();

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1);
        productCategory.setName("prdCat");
        productCategory.setDescription("description of ProdCat");
        productCategoryRepository.save(productCategory);

        Supplier supplier = new Supplier();
        supplier.setId(1);
        supplier.setName("suppName");
        supplierRepository.save(supplier);

        Product product = new Product();
        product.setId(1);
        product.setName("prodname");
        product.setDescription("predDescr");
        product.setPrice(new BigDecimal("1"));
        product.setWeight(2);
        product.setCategory(productCategory);
        product.setSupplier(supplier);
        product.setImageUrl("www.d.com");
        productRepository.save(product);

        Address address=new Address();
        address.setId(1);
        address.setCountry("Romania");
        address.setCity("ClujNapoca");
        address.setCounty("Cluj");
        address.setStreetAddress("Strada");
        addressRepository.save(address);


        Location location = new Location();
        location.setId(1);
        location.setName("LocName");
        location.setAddress(address);
        locationRepository.save(location);

        int actualQty=10;

        StockId stockId = new StockId();
        stockId.setProductId(1);
        stockId.setLocationId(1);

        stock1.setIdProductLocation(stockId);
        stock1.setProduct(product);
        stock1.setLocation(location);
        stock1.setQuantity(actualQty);
        stockRepository.save(stock1);

        int readQtyBeforeUpdate=stockService.readSingleStock(new StockId(1,1)).getQuantity();
        Assert.assertEquals(actualQty,readQtyBeforeUpdate);

        int expectedQty=0;

        StockDTO stock2=new StockDTO(1,1,product,location,expectedQty);

        stockService.updateStock(new StockId(1,1),stock2);

        int readQtyAfterUpdate=stockRepository.findAll().stream().filter(stockX -> stockX.getIdProductLocation().toString().equals((new StockId(1,1)).toString())).collect(Collectors
                .toList()).get(0).getQuantity();

        Assert.assertEquals(expectedQty,readQtyAfterUpdate);

    }
}
