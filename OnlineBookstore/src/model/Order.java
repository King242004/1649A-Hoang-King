package model;

public class Order {
    private static int counter = 1; // Tự động tăng ID cho mỗi đơn hàng
    private final int orderId;
    private Customer customer;
    private Book[] books;
    private int bookCount;

    public Order(Customer customer, int maxBooks) {
        if (customer == null || maxBooks <= 0) {
            throw new IllegalArgumentException("Customer must not be null and maxBooks must be positive.");
        }
        this.orderId = counter++;
        this.customer = customer;
        this.books = new Book[maxBooks];
        this.bookCount = 0;
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        if (bookCount < books.length) {
            books[bookCount] = book;
            bookCount = bookCount + 1;
        } else {
            throw new IllegalStateException("Cannot add more books to the order. Maximum limit reached.");
        }
    }

    public Book[] getBooks() {
        Book[] result = new Book[bookCount];
        for (int i = 0; i < bookCount; i = i + 1) {
            result[i] = books[i];
        }
        return result;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String toString() {
        String result = "Order #" + orderId + "\n";
        result = result + "Customer: " + customer.getName() + "\n";
        result = result + "Books:\n";
        for (int i = 0; i < bookCount; i = i + 1) {
            result = result + "- " + books[i].toString() + "\n";
        }
        return result;
    }
}