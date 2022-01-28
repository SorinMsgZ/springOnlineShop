package ro.msg.learning.shop.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.springframework.test.annotation.DirtiesContext;
import ro.msg.learning.shop.controller.integration_test.*;

@RunWith(Suite.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Suite.SuiteClasses({
       FailOrderCreatorRestControllerProfileTest.class,
        OrderCreatorControllerTest.class,
        OrderCreatorRestControllerTest.class,
        OrderCreatorRestControllerTestProfileTest.class,
        ProductControllerTest.class,
        ProductRestControllerTest.class,
        RevenueExportControllerTest.class,
        SecurityRestControllerTest.class,
        StockExportControllerTest.class,
        StockExportRestControllerTest.class}
)

public class ControllerITSuite {
}
