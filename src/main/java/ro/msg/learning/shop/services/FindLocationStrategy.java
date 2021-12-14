package ro.msg.learning.shop.services;


import ro.msg.learning.shop.entities.Location;

public interface FindLocationStrategy {
    Location findLocationAndTakeProducts(int productId, int productQty);
}
