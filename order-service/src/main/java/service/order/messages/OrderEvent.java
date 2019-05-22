package service.order.messages;

import service.order.Order;

public class OrderEvent {

    public enum Action {
        Checkout,
        AddStockItem,
        RemoveStockItem,
    }

    public Order order;
    public Action action;

    public OrderEvent(Order o, Action a) {
//        super(s);

        action = a;

        order = o;

    }
}
