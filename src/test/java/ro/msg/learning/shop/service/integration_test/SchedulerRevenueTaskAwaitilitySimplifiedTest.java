package ro.msg.learning.shop.service.integration_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.ShopApplication;
import ro.msg.learning.shop.services.*;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ShopApplication.class)
@TestPropertySource("classpath:test.properties")
@ActiveProfiles("TestProfile3")
public class SchedulerRevenueTaskAwaitilitySimplifiedTest {

    @SpyBean
    private SchedulerRevenueTask schedulerRevenueTask;


    @Test
    public void testAggregateAndStoreSalesRevenuesUsingAwaitility() throws InterruptedException {

        await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> verify(schedulerRevenueTask, atLeast(3)).aggregateAndStoreSalesRevenues());

    }
}
