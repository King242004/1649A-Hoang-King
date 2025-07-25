package datastructures;

public class MyStack<E> {
    private final Object[] data;
    private int top;

    public MyStack(int capacity) {
        data = new Object[capacity];
        top = -1;
    }

    public void push(E item) {
        if (top == data.length - 1) {
            System.out.println("Stack is full.");
            return;
        }
        data[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) return null;
        return (E) data[top--];
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty()) return null;
        return (E) data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    // (Tùy chọn) thêm size() nếu cần
    public int size() {
        return top + 1;
    }
}
