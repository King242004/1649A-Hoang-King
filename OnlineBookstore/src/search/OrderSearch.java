package search;

import model.Order;

public class OrderSearch {
    public static Order searchById(Order[] orders, int count, int id) {
        for (int i = 0; i < count; i++) {
            if (orders[i].getOrderId() == id) {
                return orders[i];
            }
        }
        return null;
    }
}
