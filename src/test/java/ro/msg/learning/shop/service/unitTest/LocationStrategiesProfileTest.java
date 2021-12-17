package ro.msg.learning.shop.service.unitTest;

import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.services.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class LocationStrategiesProfileTest {

    @Mock
    private FindLocationStrategy actualMockStrategy;
    @Mock
    private StockService stockService;
    @Mock
    private ProductService productService;
    private FindLocationStrategy expectedStrategy;

    @ParameterizedTest
    @EnumSource(StrategyType.class)
    void testExecuteStrategy(StrategyType inputStrategy) {
        String selectedStrategy=inputStrategy.toString();

        FindLocationStrategy expectedSingleStrategy = new SingleLocationStrategy(stockService, productService);
        FindLocationStrategy expectedMostAbundantStrategy = new MostAbundantStrategy(stockService, productService);

        Location expectedLocation = new Location();

        if (selectedStrategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            expectedLocation.setId(1);
            expectedStrategy = expectedSingleStrategy;

        } else if (selectedStrategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            expectedLocation.setId(2);
            expectedStrategy = expectedMostAbundantStrategy;
        }

        StrategyFactory strategyFactory = new StrategyFactory(selectedStrategy, stockService, productService);
        FindLocationStrategy actualNoMockStrategy = strategyFactory.getStrategy();

        Assert.assertEquals(expectedStrategy.getClass(),actualNoMockStrategy.getClass());

        Location actualLocation = new Location();
        if (actualNoMockStrategy.getClass().equals(expectedSingleStrategy.getClass())) {
            actualLocation.setId(1);

        } else if (actualNoMockStrategy.getClass().equals(expectedMostAbundantStrategy.getClass())) {
            actualLocation.setId(2);
        }

        int productId = 1;
        int productQty = 1;

        Context context = new Context(actualMockStrategy);

        when(actualMockStrategy.findLocationAndTakeProducts(anyInt(), anyInt())).thenReturn(actualLocation);

        Location actualLocationResponse = context.executeStrategy(productId, productQty);

        Assert.assertEquals(expectedLocation.getId(), actualLocationResponse.getId());
    }

}
