package ro.msg.learning.shop.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ro.msg.learning.shop.service.integration_test.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MapQuestTest.class,
        SchedulerRevenueTaskAwaitilitySimplifiedTest.class,
        SchedulerRevenueTaskAwaitilityTest.class,
        SchedulerRevenueTaskTest.class,
        StockServiceTest.class,
        SupplierServiceTest.class})
public class ServiceITSuite {
}
