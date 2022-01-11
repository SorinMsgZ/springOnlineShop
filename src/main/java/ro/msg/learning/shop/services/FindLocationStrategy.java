package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dto.OrderObjectInputDTO;

import java.util.HashMap;

public interface FindLocationStrategy {
    HashMap<Integer, Integer> findLocationAndTakeProducts(OrderObjectInputDTO input);
}
