package ro.msg.learning.shop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration

public class StrategyFactory {

    @Value("${strategy.findLocation:SingleLocationStrategy}")
    private String strategyFindLocation;

    private final SingleLocationStrategy singleLocationStrategy;
    private final MoreAbundantStrategy moreAbundantStrategy;

    public StrategyFactory(String strategyFindLocation,
                           SingleLocationStrategy singleLocationStrategy,
                           MoreAbundantStrategy moreAbundantStrategy) {
        this.strategyFindLocation = strategyFindLocation;
        this.singleLocationStrategy = singleLocationStrategy;
        this.moreAbundantStrategy = moreAbundantStrategy;
    }

    @Bean
    public FindLocationStrategy getStrategy(String strategyFindLocation) {
        if ("SingleLocationStrategy".equals(strategyFindLocation)) {
            return singleLocationStrategy;
        } else {
            return moreAbundantStrategy;
        }
    }

}
