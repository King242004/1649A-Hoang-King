package model;

import java.util.ArrayList;

public class Order {
    private static int nextOrderId = 1;

    private final int orderId;
    private final String customerName;
    private final ArrayList<Book> books;

    public Order(Customer customer) {
        this.orderId = nextOrderId++;
        this.customerName = customer.getName();
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Customer: " + customerName +
                ", Number of books: " + books.size();
    }
}
