package ro.msg.learning.shop.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ro.msg.learning.shop.repository.integration_test.CreateOrderTest;
import ro.msg.learning.shop.repository.integration_test.ProductRepositoryTest;
import ro.msg.learning.shop.service.integration_test.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateOrderTest.class,
        ProductRepositoryTest.class,
        })
public class RepositoryITSuite {
}
