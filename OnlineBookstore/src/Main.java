import java.util.Scanner;
import model.Customer;
import model.Order;
import model.Book;
import datastructures.MyQueue;
import datastructures.MyStack;
import datastructures.Sorting;
import search.OrderSearch;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyQueue<Order> queue = new MyQueue<>(10);
        MyStack<Order> stack = new MyStack<>(10);
        Order[] completedOrders = new Order[10];
        int completedCount = 0;

        boolean running = true;

        while (running) {
            System.out.println("\n===== ONLINE BOOKSTORE MENU =====");
            System.out.println("1. Place New Order");
            System.out.println("2. Process Order (Sort books & Push to Stack)");
            System.out.println("3. Pop Last Processed Order");
            System.out.println("4. Search Order by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1–5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // Nhập thông tin đơn hàng mới
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter shipping address: ");
                    String address = scanner.nextLine();

                    // ✅ Kiểm tra số điện thoại chỉ chứa số
                    String phone;
                    while (true) {
                        System.out.print("Enter phone number: ");
                        phone = scanner.nextLine();
                        if (phone.matches("\\d+")) {
                            break;
                        } else {
                            System.out.println("❌ Phone number must contain digits only. Please try again.");
                        }
                    }

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    // Tạo customer
                    Customer customer = new Customer(name, address, phone, email);

                    System.out.print("How many books in this order? ");
                    int numBooks = scanner.nextInt();
                    scanner.nextLine();

                    // ✅ Kiểm tra nếu không nhập hoặc vượt quá 5 sách thì hủy đơn
                    if (numBooks <= 0 || numBooks > 5) {
                        System.out.println("❌ You must order between 1 and 5 books. Order cancelled.");
                        break;
                    }

                    Order newOrder = new Order(customer, numBooks);

                    for (int i = 0; i < numBooks; i++) {
                        System.out.println("Book #" + (i + 1));
                        System.out.print("  Title: ");
                        String title = scanner.nextLine();
                        System.out.print("  Author: ");
                        String author = scanner.nextLine();
                        System.out.print("  Quantity: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();
                        newOrder.addBook(new Book(title, author, qty));
                    }

                    queue.enqueue(newOrder);
                    System.out.println("✅ Order placed successfully!");
                    break;

                case 2:
                    // Xử lý đơn hàng: lấy từ queue → sắp xếp → lưu vào stack + mảng
                    if (!queue.isEmpty()) {
                        Order processing = queue.dequeue();

                        System.out.println("🔎 Before sorting:");
                        for (int i = 0; i < processing.getBookCount(); i++) {
                            System.out.println(processing.getBooks()[i]);
                        }

                        Sorting.insertionSort(processing.getBooks(), processing.getBookCount());

                        System.out.println("✅ After sorting:");
                        for (int i = 0; i < processing.getBookCount(); i++) {
                            System.out.println(processing.getBooks()[i]);
                        }

                        stack.push(processing);
                        completedOrders[completedCount++] = processing;

                        System.out.println("📥 Order pushed to stack.");
                    } else {
                        System.out.println("⚠️ Queue is empty.");
                    }
                    break;

                case 3:
                    // Lấy đơn hàng cuối từ stack
                    if (!stack.isEmpty()) {
                        Order popped = stack.pop();
                        System.out.println("📤 Order popped from stack:");
                        System.out.println(popped);
                    } else {
                        System.out.println("⚠️ Stack is empty.");
                    }
                    break;

                case 4:
                    // Tìm kiếm đơn hàng theo ID
                    System.out.print("Enter order ID to search: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine();

                    Order found = OrderSearch.searchById(completedOrders, completedCount, searchId);
                    if (found != null) {
                        System.out.println("📦 Order found:");
                        System.out.println(found);
                    } else {
                        System.out.println("❌ Order not found.");
                    }
                    break;

                case 5:
                    running = false;
                    System.out.println("👋 Goodbye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
