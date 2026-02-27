package controller;

import dao.BookDAO;
import model.Book;
import java.util.List;

public class BookController {
    private BookDAO bookDAO;

    public BookController() {
        this.bookDAO = new BookDAO();
    }

    public void addBook(String title, String author, String category, int stock) {
        if (title.trim().isEmpty() || author.trim().isEmpty() || category.trim().isEmpty()) {
            System.out.println("Validasi Gagal: Semua kolom teks harus diisi penuh.");
            return;
        }

        if (stock < 0) {
            System.out.println("Validasi Gagal: Stok buku tidak boleh kurang dari nol.");
            return;
        }

        Book newBook = new Book(0, title, author, category, stock);
        bookDAO.addBook(newBook);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    
    public void deleteBook(int bookId) {
        bookDAO.deleteBook(bookId);
        System.out.println("Buku sukses dihapus dari sistem.");
    }

    public void updateBook(int id, String title, String author, String category, int stock) {
        if (title.trim().isEmpty() || author.trim().isEmpty() || category.trim().isEmpty()) {
            System.out.println("Validasi Gagal: Semua kolom teks harus diisi.");
            return;
        }
        if (stock < 0) {
            System.out.println("Validasi Gagal: Stok tidak boleh minus.");
            return;
        }
        Book updatedBook = new Book(id, title, author, category, stock);
        bookDAO.updateBook(updatedBook);
    }
}