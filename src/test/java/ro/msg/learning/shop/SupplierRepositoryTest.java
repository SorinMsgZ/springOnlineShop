package ro.msg.learning.shop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.repositories.SupplierRepository;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest

public class SupplierRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    SupplierRepository repository;

    @Test
    public void myTest() throws Exception {
//        clearDataBase();
        Supplier supplierTest = new Supplier();
        supplierTest.setName("OtherSupplier");
        entityManager.persist(supplierTest);
        entityManager.flush();
        //repository.save(supplierTest);
        //System.out.println(repository.findAll());
        Assert.assertNotNull(lookForSupplierByName("OtherSupplier"));
//       Assert.assertNotNull(lookForSupplierByName("X"));
//         clearDataBase();
    }

    public Supplier lookForSupplierByName(String repositoryName) {
        Optional<Supplier> supplierFound =
                repository.findAll().stream().filter(supplier -> supplier.getName().equals(repositoryName)).findFirst();
        return supplierFound.get();
    }

  /*  public void clearDataBase() {
        entityManager.clear();

    }*/

}
