import datastructures.ArrayListADT;
import java.util.Scanner;

import model.Customer;
import model.Order;
import model.Book;
import datastructures.MyQueue;
import datastructures.MyStack;
import datastructures.Sorting;
import search.OrderSearch;

public class Main {

    public static boolean isDigitsOnly(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') return false;
        }
        return true;
    }

    public static int simpleRandom(int min, int max) {
        long seed = System.nanoTime();
        seed = (seed * 9301 + 49297) % 233280;
        double rnd = seed / 233280.0;
        return min + (int)(rnd * (max - min + 1));
    }

    public static boolean contains(ArrayListADT<Integer> list, int value) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == value) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyQueue<Order> queue = new MyQueue<>();
        MyStack<Order> stack = new MyStack<>();
        ArrayListADT<Order> completedOrders = new ArrayListADT<>();
        ArrayListADT<Integer> sortedOrderIds = new ArrayListADT<>();

        ArrayListADT<Book> libraryBooks = new ArrayListADT<>();
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
            libraryBooks.add(new Book(title, "Various Authors", simpleRandom(5, 10)));
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
            System.out.println("6. View Processed and Unprocessed Orders");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1–7): ");

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
                        if (isDigitsOnly(phone)) break;
                        System.out.println("❌ Phone number must contain digits only.");
                    }

                    String email;
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scanner.nextLine();
                        if (email.endsWith("@gmail.com")) break;
                        System.out.println("❌ Email must end with '@gmail.com'. Please try again.");
                    }

                    Customer customer = new Customer(name, address, phone, email);
                    System.out.print("How many books in this order? (1–5): ");
                    int numBooks = scanner.nextInt();
                    scanner.nextLine();

                    if (numBooks < 1 || numBooks > 5) {
                        System.out.println("❌ Invalid number of books.");
                        break;
                    }

                    Order newOrder = new Order(customer);
                    for (int i = 0; i < numBooks; i++) {
                        System.out.println("\nBook #" + (i + 1));
                        System.out.println("📚 Available books:");
                        int count = 1;
                        for (Book book : libraryBooks) {
                            System.out.println((count++) + ". " + book);
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
                                System.out.println("❌ Not enough stock.");
                                continue;
                            }

                            libBook.setQuantity(libBook.getQuantity() - qty);
                            Book book = new Book(libBook.getTitle(), libBook.getAuthor(), qty);
                            newOrder.addBook(book);
                        } else {
                            System.out.println("❌ Invalid selection.");
                        }
                    }

                    queue.offer(newOrder);
                    System.out.println("✅ Order placed successfully.");
                    break;

                case 2:
                    System.out.println("\n⚙️ === CASE 2: Sort Next Unsorted Order in Queue ===");

                    if (queue.isEmpty()) {
                        System.out.println("⚠️ Queue is empty.");
                        break;
                    }

                    boolean sorted = false;
                    int size = queue.size();

                    for (int i = 0; i < size; i++) {
                        Order order = queue.poll();

                        if (!contains(sortedOrderIds, order.getOrderId()) && !sorted) {
                            System.out.println("🔎 Sorting Order ID: " + order.getOrderId() +
                                    ", Customer: " + order.getCustomerName());

                            System.out.println("📚 Before sort:");
                            ArrayListADT<Book> books = order.getBooks();
                            for (Book book : books) {
                                System.out.println("   - " + book);
                            }

                            Sorting.quickSort(books);

                            System.out.println("✅ After sort:");
                            for (Book book : books) {
                                System.out.println("   - " + book);
                            }

                            stack.push(order);
                            completedOrders.add(order);
                            sortedOrderIds.add(order.getOrderId());
                            System.out.println("📥 Order pushed to stack.");
                            sorted = true;
                        } else {
                            queue.offer(order);
                        }
                    }

                    if (!sorted) {
                        System.out.println("✅ All orders have already been sorted.");
                    }
                    break;

                case 3:
                    System.out.println("\n📤 === CASE 3: Pop Last Processed Order ===");
                    if (!stack.isEmpty()) {
                        Order popped = stack.pop();
                        System.out.println("📤 Popped Order: " + popped);
                        System.out.println("📚 Books in this order:");
                        for (Book book : popped.getBooks()) {
                            System.out.println(" - " + book.getTitle() + " by " + book.getAuthor() + " (Qty: " + book.getQuantity() + ")");
                        }
                    } else {
                        System.out.println("⚠️ Stack is empty.");
                    }

                case 4:
                    System.out.println("\n🔍 === CASE 4: Search Order by ID ===");
                    System.out.print("Enter Order ID to search: ");
                    int searchId = scanner.nextInt();
                    scanner.nextLine();

                    Order result = OrderSearch.searchById(completedOrders, searchId);
                    if (result != null) {
                        System.out.println("📦 Order found: " + result);
                        for (Book b : result.getBooks()) {
                            System.out.println(" - " + b.getTitle() + " by " + b.getAuthor() + " (Qty: " + b.getQuantity() + ")");
                        }
                    } else {
                        System.out.println("❌ Order not found.");
                    }
                    break;

                case 5:
                    System.out.println("\n📚 === CASE 5: Available Stock ===");
                    int idx = 1;
                    for (Book book : libraryBooks) {
                        System.out.println((idx++) + ". " + book);
                    }
                    break;

                case 6:
                    System.out.println("\n📊 === CASE 6: View Processed and Unprocessed Orders ===");

                    System.out.println("🔄 Unprocessed Orders:");
                    if (queue.isEmpty()) {
                        System.out.println("(None)");
                    } else {
                        MyQueue<Order> temp = new MyQueue<>();
                        while (!queue.isEmpty()) {
                            Order order = queue.poll();
                            System.out.println("- Order ID: " + order.getOrderId() + ", Customer: " + order.getCustomerName());
                            for (Book book : order.getBooks()) {
                                System.out.println("   + " + book.getTitle() + " by " + book.getAuthor() + " (Qty: " + book.getQuantity() + ")");
                            }
                            temp.offer(order);
                        }
                        while (!temp.isEmpty()) {
                            queue.offer(temp.poll());
                        }
                    }

                    System.out.println("\n✅ Processed Orders:");
                    if (completedOrders.size() == 0) {
                        System.out.println("(None)");
                    } else {
                        for (Order order : completedOrders) {
                            System.out.println("- Order ID: " + order.getOrderId() + ", Customer: " + order.getCustomerName());
                            for (Book book : order.getBooks()) {
                                System.out.println("   + " + book.getTitle() + " by " + book.getAuthor() + " (Qty: " + book.getQuantity() + ")");
                            }
                        }
                    }
                    break;

                case 7:
                    System.out.println("\n👋 Exiting program.");
                    running = false;
                    break;

                default:
                    System.out.println("❗ Invalid choice.");
            }
        }

        scanner.close();
    }
}
