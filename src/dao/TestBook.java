
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Brian
 */

package dao;

import model.Book;

public class TestBook {
    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("OOP in JAVA");
        book.setAuthor("Brian Zakariya");
        book.setCategory("Programming");
        book.setStock(10);
        
        BookDAO dao = new BookDAO();
        dao.insert(book);
    } 
}
