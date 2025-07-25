package datastructures;

public class MyQueue<E> {
    private final Object[] data;
    private final int capacity;
    private int front;
    private int rear;
    private int size;

    public MyQueue(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // ✅  offer
    public void offer(E item) {
        if (size == capacity) {
            System.out.println("Queue is full.");
            return;
        }
        rear = (rear + 1) % capacity;
        data[rear] = item;
        size++;
    }

    // ✅  poll (lấy và xóa phần tử đầu)
    @SuppressWarnings("unchecked")
    public E poll() {
        if (isEmpty()) return null;
        E item = (E) data[front];
        data[front] = null; // optional: clear reference
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
