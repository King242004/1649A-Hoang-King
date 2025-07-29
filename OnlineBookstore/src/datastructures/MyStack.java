package datastructures;

public class MyStack<E> {

    // ✅ Lớp Node nội bộ để xây dựng danh sách liên kết đơn
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    // ✅ Con trỏ tới phần tử đầu stack (đỉnh)
    private Node<E> top;
    private int size;

    // ✅ Khởi tạo stack rỗng
    public MyStack() {
        top = null;
        size = 0;
    }

    // ✅ Thêm phần tử vào đỉnh stack (LIFO)
    public void push(E item) {
        Node<E> newNode = new Node<>(item); // tạo node mới
        newNode.next = top;                 // liên kết node mới với node hiện tại
        top = newNode;                      // cập nhật đỉnh stack
        size++;                             // tăng kích thước stack
    }

    // ✅ Lấy và xóa phần tử đỉnh stack
    public E pop() {
        if (isEmpty()) return null;         // kiểm tra rỗng
        E item = top.data;                  // lấy dữ liệu
        top = top.next;                     // bỏ node đầu (di chuyển top)
        size--;                             // giảm kích thước
        return item;
    }

    // ✅ Xem phần tử đỉnh mà không xóa
    public E peek() {
        if (isEmpty()) return null;
        return top.data;
    }

    // ✅ Kiểm tra stack rỗng
    public boolean isEmpty() {
        return top == null;
    }

    // ✅ Trả về số phần tử trong stack
    public int size() {
        return size;
    }

    // ✅ Xóa toàn bộ stack
    public void clear() {
        top = null;
        size = 0;
    }
}
