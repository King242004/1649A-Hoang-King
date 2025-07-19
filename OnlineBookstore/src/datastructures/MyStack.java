package datastructures;

public class MyStack<T> {
    private final Object[] data;
    private int top;

    public MyStack(int capacity) {
        data = new Object[capacity];
        top = -1;
    }

    public void push(T item) {
        if (top == data.length - 1) {
            System.out.println("Stack is full.");
            return;
        }
        data[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) return null;
        return (T) data[top--];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) return null;
        return (T) data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}