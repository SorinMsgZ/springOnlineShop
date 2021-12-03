package ro.msg.learning.shop.services;

public abstract class ObserverObject {
    protected OrderBasket subject;

    protected ObserverObject(OrderBasket subject) {
        this.subject = subject;
    }

    public abstract void update();
}
