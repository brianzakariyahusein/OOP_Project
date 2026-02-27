package dao;

import model.Book;
import java.util.List;

public class TestBook {
    public static void main(String[] args) {

        BookDAO dao = new BookDAO();
        List<Book> books = dao.getAll();

        for (Book b : books) {
            System.out.println(
                b.getId() + " - " +
                b.getTitle() + " - " +
                b.getAuthor() + " - " +
                b.getCategory() + " - " +
                b.getStock()
            );
        }
    }
}