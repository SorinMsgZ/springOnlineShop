package ro.msg.learning.shop.services;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class StrategyFactory {

    private final String strategyFindLocation;
    private final StockService stockService;
    private final ProductService productService;

    @Autowired
    public StrategyFactory(@Value("${strategy.findLocation}") String strategyFindLocation,StockService stockService,ProductService productService) {
        this.strategyFindLocation = strategyFindLocation;
        this.stockService=stockService;
        this.productService=productService;
    }

    @Bean
    @Primary
    public FindLocationStrategy getStrategy() {
        if (StrategyType.SINGLE_LOCATION_STRATEGY.toString().equals(strategyFindLocation)) {
            return new SingleLocationStrategy(stockService,productService);
        } else if (StrategyType.MOST_ABUNDANT_STRATEGY.toString().equals(strategyFindLocation)) {
            return new MostAbundantStrategy(stockService,productService);
        }
        return new SingleLocationStrategy(stockService,productService);
    }

}
