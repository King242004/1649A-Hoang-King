package datastructures;

public class MyStack<E> {

    // ✅ Node nội bộ
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> top; // đỉnh stack (LIFO)
    private int size;

    public MyStack() {
        top = null;
        size = 0;
    }

    // ✅ push: thêm phần tử vào đầu stack
    public void push(E item) {
        Node<E> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    // ✅ pop: lấy và xóa phần tử đầu stack
    public E pop() {
        if (isEmpty()) return null;
        E item = top.data;
        top = top.next;
        size--;
        return item;
    }

    // ✅ peek: chỉ lấy phần tử đầu stack, không xóa
    public E peek() {
        if (isEmpty()) return null;
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
