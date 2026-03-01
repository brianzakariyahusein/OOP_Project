package model;

import java.sql.Timestamp;

public class BookModel {

    private int bookId;
    private String title;
    private String author;
    private String category;
    private int yearPublished;
    private int stock;
    private Timestamp createdAt;

    public BookModel() {
    }

    // Konstruktor Lengkap (untuk Load Data)
    public BookModel(int bookId, String title, String author, String category, int yearPublished, int stock, Timestamp createdAt) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        this.stock = stock;
        this.createdAt = createdAt;
    }

    // Konstruktor untuk Tambah/Edit
    public BookModel(int bookId, String title, String author, String category, int yearPublished, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.yearPublished = yearPublished;
        this.stock = stock;
    }

    // Getter & Setter
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
