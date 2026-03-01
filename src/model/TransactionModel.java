/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Brian
 */
public class TransactionModel {

    private int transactionId;
    private int memberId;
    private int bookId;
    private int processedBy;
    private Date borrowDate;
    private Date returnDate;
    private String status;

    public TransactionModel() {
    }

    public TransactionModel(int memberId, int bookId, int processedBy, Date borrowDate, String status) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.processedBy = processedBy;
        this.borrowDate = borrowDate;
        this.status = status;
    }

    public TransactionModel(int transactionId, int memberId, int bookId, int processedBy, Date borrowDate, Date returnDate, String status) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.processedBy = processedBy;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(int processedBy) {
        this.processedBy = processedBy;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
