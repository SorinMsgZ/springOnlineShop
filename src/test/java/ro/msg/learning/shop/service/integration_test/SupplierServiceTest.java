package ro.msg.learning.shop.service.integration_test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.repositories.SupplierRepository;
import ro.msg.learning.shop.services.SupplierService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class SupplierServiceTest {
    @Autowired
    SupplierService supplierService;
    @Autowired
    SupplierRepository supplierRepository;

    @Test
    public void testGenerateAutomatedId() {
        SupplierDTO sup1 = new SupplierDTO("NameSupplier1");
        SupplierDTO sup2 = new SupplierDTO("NameSupplier2");
        SupplierDTO sup3 = new SupplierDTO("NameSupplier3");

        supplierService.create(sup1);
        supplierService.create(sup2);
        supplierService.create(sup3);

        int supplierNbService = supplierService.listAll().size();
        int supplierNbRepo = supplierRepository.findAll().size();
        int idOfSupplierOne = supplierRepository.findByName("NameSupplier1").orElseThrow().getId();

        Assert.assertEquals(3, supplierNbRepo);
        Assert.assertEquals(supplierNbService, supplierNbRepo);
        Assert.assertEquals(1, idOfSupplierOne);
    }
}