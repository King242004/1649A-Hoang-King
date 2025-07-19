package datastructures;

import model.Book;

public class Sorting {
    public static void insertionSort(Book[] books, int length) {
        for (int i = 1; i < length; i++) {
            Book key = books[i];
            int j = i - 1;
            while (j >= 0 && books[j].getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                books[j + 1] = books[j];
                j--;
            }
            books[j + 1] = key;
        }
    }
}