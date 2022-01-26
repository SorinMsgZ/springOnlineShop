package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderObjectInputDTO;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class Context {

    private final FindLocationStrategy strategy;

    public Map<Integer, Integer> executeStrategy(OrderObjectInputDTO input) {
        return strategy.findLocationAndTakeProducts(input);
    }
}
