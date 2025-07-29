package datastructures;

import model.Book;

public class Sorting {

    public enum SortType {
    }

    public static void quickSort(ArrayListADT<Book> books) {
        quickSortHelper(books, 0, books.size() - 1);
    }

    private static void quickSortHelper(ArrayListADT<Book> books, int low, int high) {
        if (low < high) {
            int pi = partition(books, low, high);
            quickSortHelper(books, low, pi - 1);
            quickSortHelper(books, pi + 1, high);
        }
    }

    private static int partition(ArrayListADT<Book> books, int low, int high) {
        Book pivot = books.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (customCompareIgnoreCase(books.get(j).getTitle(), pivot.getTitle()) <= 0) {
                i++;
                swap(books, i, j);
            }
        }

        swap(books, i + 1, high);
        return i + 1;
    }

    private static void swap(ArrayListADT<Book> books, int i, int j) {
        Book temp = books.get(i);
        books.set(i, books.get(j));
        books.set(j, temp);
    }

    // Hàm so sánh chuỗi không phân biệt hoa thường
    private static int customCompareIgnoreCase(String s1, String s2) {
        s1 = toLowerCase(s1);
        s2 = toLowerCase(s2);
        int minLen = s1.length() < s2.length() ? s1.length() : s2.length();
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return s1.charAt(i) - s2.charAt(i);
            }
        }
        return s1.length() - s2.length();
    }

    // Hàm chuyển chữ in hoa thành chữ thường (tự cài)
    private static String toLowerCase(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] = (char)(chars[i] + ('a' - 'A'));
            }
        }
        return new String(chars);
    }
}
