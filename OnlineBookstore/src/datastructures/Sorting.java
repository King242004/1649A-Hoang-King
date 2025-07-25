package datastructures;

import model.Book;
import java.util.ArrayList;

public class Sorting {

    public enum SortType {
    }

    public static void quickSort(ArrayList<Book> books) {
        quickSortHelper(books, 0, books.size() - 1);
    }

    private static void quickSortHelper(ArrayList<Book> books, int low, int high) {
        if (low < high) {
            int pi = partition(books, low, high);
            quickSortHelper(books, low, pi - 1);
            quickSortHelper(books, pi + 1, high);
        }
    }

    private static int partition(ArrayList<Book> books, int low, int high) {
        Book pivot = books.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (books.get(j).getTitle().compareToIgnoreCase(pivot.getTitle()) <= 0) {
                i++;
                swap(books, i, j);
            }
        }

        swap(books, i + 1, high);
        return i + 1;
    }

    private static void swap(ArrayList<Book> books, int i, int j) {
        Book temp = books.get(i);
        books.set(i, books.get(j));
        books.set(j, temp);
    }
}
