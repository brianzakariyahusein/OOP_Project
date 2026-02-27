
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Brian
 */
package dao;

import config.DatabaseConnection;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void insert(Book book) {
        String sql = "INSERT INTO books (title, author,category, stock) VALUES (?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, book.getTitle());
            pst.setString(2, book.getAuthor());
            pst.setString(3, book.getCategory());
            pst.setInt(4, book.getStock());

            pst.executeUpdate();
            System.out.println("Book inserted successfully!");

        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }
    
    public List<Book> getAll() {
    List<Book> list = new ArrayList<>();
    String sql = "SELECT * FROM books";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setCategory(rs.getString("category"));
            book.setStock(rs.getInt("stock"));

            list.add(book);
        }

    } catch (SQLException e) {
        System.out.println("Fetch failed: " + e.getMessage());
    }

    return list;
}
}
