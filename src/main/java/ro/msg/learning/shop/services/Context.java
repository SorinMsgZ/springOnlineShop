package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.Location;

@Service
@RequiredArgsConstructor
public class Context {

    private final FindLocationStrategy strategy;

    public Location executeStrategy(int productId, int productQty) {
        return strategy.findLocationAndTakeProducts(productId, productQty);
    }
}
