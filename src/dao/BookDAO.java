package dao;

import config.DatabaseConnection;
import model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection conn;

    public BookDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // Fungsi untuk menambah buku baru ke database
    public void addBook(Book book) {
        String query = "INSERT INTO books (title, author, category, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCategory());
            stmt.setInt(4, book.getStock());
            
            stmt.executeUpdate();
            System.out.println("Buku berhasil ditambahkan!");
        } catch (SQLException e) {
            System.out.println("Gagal tambah buku: " + e.getMessage());
        }
    }

    // Fungsi untuk mengambil semua data buku, berguna untuk ditampilkan di JTable NetBeans
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setStock(rs.getInt("stock"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data buku: " + e.getMessage());
        }
        return books;
    }
    
    public void deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus buku: " + e.getMessage());
        }
    }
    
    public void updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, category = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCategory());
            stmt.setInt(4, book.getStock());
            stmt.setInt(5, book.getId());
            stmt.executeUpdate();
            System.out.println("Data buku berhasil diperbarui!");
        } catch (SQLException e) {
            System.out.println("Gagal update buku: " + e.getMessage());
        }
    }

    public void updateStock(int bookId, int amount) {
        // amount bisa positif (tambah stok) atau negatif (kurangi stok)
        String query = "UPDATE books SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, amount);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal update stok: " + e.getMessage());
        }
    }
    
    // Fungsi untuk memvalidasi stok sebelum dipinjam
    public int getBookStock(int bookId) {
        String query = "SELECT stock FROM books WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("stock");
        } catch (SQLException e) {
            System.out.println("Gagal cek stok: " + e.getMessage());
        }
        return 0;
    }
}