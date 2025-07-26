package search;

import model.Order;
import java.util.List;

public class OrderSearch {
    public static Order searchById(List<Order> orders, int id) {
        for (Order order : orders) {
            if (order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }
}
