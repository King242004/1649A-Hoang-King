package datastructures;

import java.util.Iterator;

public class ArrayListADT<E> implements Iterable<E> {
    private Object[] data;
    private int size;

    public ArrayListADT() {
        data = new Object[10];
        size = 0;
    }

    public void add(E element) {
        if (size == data.length) resize();
        data[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    public void set(int index, E element) {
        checkIndex(index);
        data[index] = element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public E removeLast() {
        if (size == 0) {
            throw new IllegalStateException("List is empty");
        }
        E element = (E) data[size - 1];
        data[size - 1] = null; // Clear last element
        size--;
        return element;
    }

    private void resize() {
        Object[] newData = new Object[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<E> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            return (E) data[current++];
        }
    }
}
