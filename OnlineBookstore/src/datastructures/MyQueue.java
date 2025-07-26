package datastructures;

public class MyQueue<E> {
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> front;  // Điểm đầu của hàng đợi
    private Node<E> rear;   // Điểm cuối của hàng đợi
    private int size;

    public MyQueue() {
        front = rear = null;
        size = 0;
    }

    // ✅ offer: thêm phần tử vào cuối hàng đợi
    public void offer(E item) {
        Node<E> newNode = new Node<>(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // ✅ poll: lấy và xóa phần tử ở đầu hàng đợi
    public E poll() {
        if (isEmpty()) return null;
        E item = front.data;
        front = front.next;
        if (front == null) rear = null; // Nếu hàng trống sau khi lấy
        size--;
        return item;
    }

    // ✅ kiểm tra rỗng
    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}
