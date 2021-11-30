package ro.msg.learning.shop.services;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entities.Location;

public interface FindLocationStrategy {
    public Location findLocationAndTakeProducts(int productId, int productQty) ;
}
