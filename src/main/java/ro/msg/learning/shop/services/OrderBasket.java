package ro.msg.learning.shop.services;

import java.util.ArrayList;
import java.util.List;

public class OrderBasket implements SubjectOrderBasket {
    private List<ObserverObject> observers = new ArrayList<>();
    private OrderObject orderObject;

    public List<ObserverObject> getObservers() {
        return observers;
    }

    public void setObservers(List<ObserverObject> observers) {
        this.observers = observers;
    }

    public OrderObject getOrderObject() {
        return orderObject;
    }

    public void setOrderObject(OrderObject orderObject) {
        this.orderObject = orderObject;
    }

    @Override
    public void attach(ObserverObject observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (ObserverObject observer : observers) {
            observer.update();
        }
    }
}
