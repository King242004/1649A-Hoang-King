import java.util.ArrayList;
import java.util.HashSet;
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

        // ✅ Biến theo dõi các Order ID đã sort
        HashSet<Integer> sortedOrderIds = new HashSet<>();

        ArrayList<Book> libraryBooks = new ArrayList<>();
        String[] titles = {
                "Refactoring", "Clean Architecture", "Cracking the Coding Interview", "You Don't Know JS",
                "Grokking Algorithms", "Effective Java", "Design Patterns", "Java Concurrency in Practice",
                "The Pragmatic Programmer", "Head First Design Patterns", "Code Complete", "Java: The Complete Reference",
                "Structure and Interpretation of Computer Programs", "Algorithms (Robert Sedgewick)", "Introduction to Algorithms",
                "Python Crash Course", "JavaScript: The Good Parts", "HTML and CSS: Design and Build Websites",
                "Eloquent JavaScript", "Learning Python", "Automate the Boring Stuff with Python", "Spring in Action",
                "C Programming Language", "Programming Pearls", "Computer Networks", "Operating System Concepts",
                "Modern Operating Systems", "Clean Code", "Refactoring UI", "Soft Skills", "Don't Make Me Think",
                "The Mythical Man-Month", "Deep Work", "Artificial Intelligence: A Modern Approach", "Data Structures and Algorithms Made Easy",
                "Elements of Programming Interviews", "Grokking System Design", "Java Performance", "Fundamentals of Database Systems",
                "SQL for Dummies", "MongoDB: The Definitive Guide", "Learning SQL", "NoSQL Distilled", "Agile Software Development",
                "User Story Mapping", "Scrum: The Art of Doing Twice the Work", "Extreme Programming Explained",
                "Hands-On Machine Learning", "Pattern Recognition and Machine Learning", "Deep Learning with Python",
                "Computer Organization and Design", "Digital Design", "Microprocessor Architecture", "Networking All-in-One",
                "Kubernetes Up and Running", "Docker Deep Dive", "Linux Pocket Guide", "Linux Command Line and Shell Scripting",
                "Cloud Computing Basics", "AWS Certified Solutions Architect", "Terraform: Up & Running", "CI/CD Pipeline Design",
                "DevOps Handbook", "Cybersecurity Essentials", "Penetration Testing", "Hacking: The Art of Exploitation",
                "CompTIA Security+ Guide", "Game Programming Patterns", "Unity in Action", "Unreal Engine Essentials",
                "OpenGL Programming Guide", "Graphics Programming in Java", "3D Math Primer"
        };
        for (String title : titles) {
            libraryBooks.add(new Book(title, "Various Authors", 5 + (int)(Math.random() * 6)));
        }

        boolean running = true;

        while (running) {
            System.out.println("\n---------------------------------------");
            System.out.println("===== ONLINE BOOKSTORE MENU =====");
            System.out.println("1. Place New Order");
            System.out.println("2. Process Order (Sort books by Order ID)");
            System.out.println("3. Pop Last Processed Order");
            System.out.println("4. Search Order by ID");
            System.out.println("5. View Available Stock");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1–6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n🛒 === CASE 1: Place New Order ===");
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter shipping address: ");
                    String address = scanner.nextLine();

                    String phone;
                    while (true) {
                        System.out.print("Enter phone number: ");
                        phone = scanner.nextLine();
                        if (phone.matches("\\d+")) break;
                        System.out.println("❌ Phone number must contain digits only. Please try again.");
                    }

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    Customer customer = new Customer(name, address, phone, email);

                    System.out.print("How many books in this order? ");
                    int numBooks = scanner.nextInt();
                    scanner.nextLine();

                    if (numBooks <= 0 || numBooks > 5) {
                        System.out.println("❌ You must order between 1 and 5 books. Order cancelled.");
                        break;
                    }

                    Order newOrder = new Order(customer);

                    for (int i = 0; i < numBooks; i++) {
                        System.out.println("Book #" + (i + 1));

                        String useLibrary;
                        while (true) {
                            System.out.print("Use book from library? (y/n): ");
                            useLibrary = scanner.nextLine().trim().toLowerCase();
                            if (useLibrary.equals("y") || useLibrary.equals("n")) break;
                            System.out.println("❌ Invalid input. Please enter only 'y' or 'n'.");
                        }

                        Book book;

                        if (useLibrary.equals("y")) {
                            System.out.println("📚 Available books:");
                            for (int j = 0; j < libraryBooks.size(); j++) {
                                System.out.println((j + 1) + ". " + libraryBooks.get(j));
                            }

                            System.out.print("Choose book number: ");
                            int index = scanner.nextInt() - 1;
                            scanner.nextLine();

                            if (index >= 0 && index < libraryBooks.size()) {
                                Book libBook = libraryBooks.get(index);

                                System.out.print("Enter quantity: ");
                                int qty = scanner.nextInt();
                                scanner.nextLine();

                                if (qty > libBook.getQuantity()) {
                                    System.out.println("❌ Not enough stock. Only " + libBook.getQuantity() + " available. Skipping.");
                                    continue;
                                }

                                libBook.setQuantity(libBook.getQuantity() - qty);
                                book = new Book(libBook.getTitle(), libBook.getAuthor(), qty);
                            } else {
                                System.out.println("❌ Invalid book selection. Skipping.");
                                continue;
                            }

                        } else {
                            System.out.print("  Title: ");
                            String title = scanner.nextLine();
                            System.out.print("  Author: ");
                            String author = scanner.nextLine();
                            System.out.print("  Quantity: ");
                            int qty = scanner.nextInt();
                            scanner.nextLine();
                            book = new Book(title, author, qty);
                        }

                        newOrder.addBook(book);
                    }

                    queue.offer(newOrder);
                    System.out.println("✅ Order placed successfully!");
                    break;

                case 2:
                    System.out.println("\n⚙️ === CASE 2: Sort Books in Specific Order by ID ===");

                    if (queue.isEmpty()) {
                        System.out.println("⚠️ Queue is empty.");
                        break;
                    }

                    System.out.print("Enter Order ID to sort books: ");
                    int targetId = scanner.nextInt();
                    scanner.nextLine();

                    if (sortedOrderIds.contains(targetId)) {
                        System.out.println("⚠️ Order ID " + targetId + " has already been sorted.");
                        break;
                    }

                    boolean orderFound = false;
                    int queueSize = queue.size();

                    for (int i = 0; i < queueSize; i++) {
                        Order currentOrder = queue.poll();

                        if (currentOrder.getOrderId() == targetId) {
                            orderFound = true;

                            System.out.println("👤 Customer: " + currentOrder.getCustomerName());

                            System.out.println("🔎 Before sorting:");
                            for (Book book : currentOrder.getBooks()) {
                                System.out.println(book);
                            }

                            Sorting.quickSort((ArrayList<Book>) currentOrder.getBooks());

                            System.out.println("✅ After sorting:");
                            for (Book book : currentOrder.getBooks()) {
                                System.out.println(book);
                            }

                            stack.push(currentOrder);
                            completedOrders[completedCount++] = currentOrder;
                            sortedOrderIds.add(targetId);

                            System.out.println("📥 Order pushed to stack.");
                        }

                        queue.offer(currentOrder);
                    }

                    if (!orderFound) {
                        System.out.println("❌ Order with ID " + targetId + " not found in the queue.");
                    }

                    break;

                case 3:
                    System.out.println("\n📤 === CASE 3: Pop Last Processed Order ===");
                    if (!stack.isEmpty()) {
                        Order popped = stack.pop();
                        System.out.println("📤 Order popped from stack:");
                        System.out.println(popped);
                    } else {
                        System.out.println("⚠️ Stack is empty.");
                    }
                    break;

                case 4:
                    System.out.println("\n🔍 === CASE 4: Search Order by ID ===");
                    System.out.print("Enter order ID to search: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine();

                    Order found = OrderSearch.searchById(completedOrders, completedCount, searchId);
                    if (found != null) {
                        System.out.println("📦 Order found:");
                        System.out.println(found);

                        System.out.println("📚 Books in this order:");
                        for (Book book : found.getBooks()) {
                            System.out.println(" - " + book);
                        }
                    } else {
                        System.out.println("❌ Order not found.");
                    }
                    break;

                case 5:
                    System.out.println("\n📚 === CASE 5: Available Stock ===");
                    int index = 1;
                    for (Book book : libraryBooks) {
                        System.out.println(index++ + ". " + book);
                    }
                    break;


                case 6:
                    System.out.println("\n👋 === Exiting program ===");
                    running = false;
                    break;

                default:
                    System.out.println("❗ Invalid choice. Please select from 1 to 6.");
            }
        }

        scanner.close();
    }
}
