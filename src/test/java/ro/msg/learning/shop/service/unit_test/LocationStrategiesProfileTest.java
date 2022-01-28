package ro.msg.learning.shop.service.unit_test;

import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import ro.msg.learning.shop.dto.OrderObjectInputDTO;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.services.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class LocationStrategiesProfileTest {

    @Mock
    private FindLocationStrategy actualMockStrategy;
    @Mock
    private StockService stockService;
    @Mock
    private ProductService productService;
    private FindLocationStrategy expectedStrategy;

    @ParameterizedTest
    @EnumSource(StrategyType.class)
    public void testExecuteStrategy(StrategyType inputStrategy) {
        String selectedStrategy = inputStrategy.toString();

        FindLocationStrategy expectedSingleStrategy = new SingleLocationStrategy(stockService, productService);
        FindLocationStrategy expectedMostAbundantStrategy = new MostAbundantStrategy(stockService, productService);
        FindLocationStrategy expectedGreedyStrategy = new GreedyStrategy(stockService, productService);

        Location expectedLocation = new Location();

        if (selectedStrategy.equals(StrategyType.SINGLE_LOCATION_STRATEGY.toString())) {
            expectedLocation.setId(1);
            expectedStrategy = expectedSingleStrategy;

        } else if (selectedStrategy.equals(StrategyType.MOST_ABUNDANT_STRATEGY.toString())) {
            expectedLocation.setId(2);
            expectedStrategy = expectedMostAbundantStrategy;
        } else if (selectedStrategy.equals(StrategyType.PROXIMITY_STRATEGY.toString())) {
            expectedLocation.setId(3);
            expectedStrategy = expectedGreedyStrategy;
        }

        StrategyFactory strategyFactory = new StrategyFactory(selectedStrategy, stockService, productService);
        FindLocationStrategy actualNoMockStrategy = strategyFactory.getStrategy();

        Assert.assertEquals(expectedStrategy.getClass(), actualNoMockStrategy.getClass());

        Location actualLocation = new Location();
        if (actualNoMockStrategy.getClass().equals(expectedSingleStrategy.getClass())) {
            actualLocation.setId(1);

        } else if (actualNoMockStrategy.getClass().equals(expectedMostAbundantStrategy.getClass())) {
            actualLocation.setId(2);
        } else if (actualNoMockStrategy.getClass().equals(expectedGreedyStrategy.getClass())) {
            actualLocation.setId(3);
        }
        int productId = 1;
        int productQty = 1;

        HashMap<Integer, Integer> productLocation = new HashMap<>();
        productLocation.put(productId, actualLocation.getId());

        OrderObjectInputDTO input = new OrderObjectInputDTO();

        Context context = new Context(actualMockStrategy);

        when(actualMockStrategy.findLocationAndTakeProducts(any())).thenReturn(productLocation);


        Map<Integer, Integer> actualProductLocationResponse = context.executeStrategy(input);

        Assert.assertEquals(java.util.Optional.of(expectedLocation.getId()), java.util.Optional
                .ofNullable(actualProductLocationResponse.get(1)));
    }

}
