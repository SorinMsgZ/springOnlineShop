package ro.msg.learning.shop.services;

public abstract class ObserverObject {
    protected OrderBasket subject ;

    public ObserverObject(OrderBasket subject) {
        this.subject = subject;
    }

    public abstract void update();
}
