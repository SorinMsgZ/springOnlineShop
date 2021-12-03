package ro.msg.learning.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;

@Service
@Configuration

public class StrategyFactory {

    private final String strategyFindLocation;
    private final SingleLocationStrategy singleLocationStrategy;
    private final MoreAbundantStrategy moreAbundantStrategy;

    @Autowired
    public StrategyFactory(@Value("${strategy.findLocation:SingleLocationStrategy}") String strategyFindLocation,
                           SingleLocationStrategy singleLocationStrategy,
                           MoreAbundantStrategy moreAbundantStrategy) {
        this.strategyFindLocation = strategyFindLocation;
        this.singleLocationStrategy = singleLocationStrategy;
        this.moreAbundantStrategy = moreAbundantStrategy;
    }

    @Bean
    @Primary
    public FindLocationStrategy getStrategy() {
        if ("SingleLocationStrategy".equals(strategyFindLocation)) {
            return singleLocationStrategy;
        } else if ("MoreAbundantStrategy".equals(strategyFindLocation)) {
            return moreAbundantStrategy;
        }
        return singleLocationStrategy;
    }

}
