package dao;

import model.Book;

public class TestBook {
    public static void main(String[] args) {

        BookDAO dao = new BookDAO();
        
        Book book = new Book();
        book.setId(1);
        book.setTitle("Advanced Java OOP");
        book.setAuthor("Brian Jakariya");
        book.setCategory("Programming Advanced Level");
        book.setStock(50);
        
        dao.update(book);
    }
}