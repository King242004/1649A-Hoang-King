package search;

import model.Order;
import datastructures.ArrayListADT;

public class OrderSearch {
    public static Order searchById(ArrayListADT<Order> orders, int id) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }
}
