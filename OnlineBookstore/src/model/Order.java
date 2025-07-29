package model;

import datastructures.ArrayListADT;

public class Order {
    private static int nextOrderId = 1;

    private final int orderId;
    private final String customerName;
    private final ArrayListADT<Book> books;

    public Order(Customer customer) {
        this.orderId = nextOrderId++;
        this.customerName = customer.getName();
        this.books = new ArrayListADT<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayListADT<Book> getBooks() {
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
