package ro.msg.learning.shop.services;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.Location;

@Service
public class Context {
    private FindLocationStrategy strategy;

    public Context(FindLocationStrategy strategy) {
        this.strategy = strategy;
    }

    public Location executeStrategy(int productId, int productQty) {
        return strategy.findLocationAndTakeProducts(productId, productQty);
    }
}
