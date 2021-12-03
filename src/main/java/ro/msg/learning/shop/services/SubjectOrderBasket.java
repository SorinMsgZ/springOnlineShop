package ro.msg.learning.shop.services;

public interface SubjectOrderBasket {
    void attach(ObserverObject observer);

    void notifyAllObservers();
}
