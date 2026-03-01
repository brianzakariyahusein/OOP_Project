/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.TransactionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brian
 */
public class TransactionDAO {

    public List<TransactionModel> getAllTransactions() {
        List<TransactionModel> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TransactionModel(
                        rs.getInt("transaction_id"),
                        rs.getInt("member_id"),
                        rs.getInt("book_id"),
                        rs.getInt("processed_by"),
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date"),
                        rs.getString("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean borrowBook(TransactionModel transaction) {
        String sql = "INSERT INTO transactions (member_id, book_id, processed_by, borrow_date, status) VALUES (?, ?, ?, ?, 'borrowed')";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transaction.getMemberId());
            ps.setInt(2, transaction.getBookId());
            ps.setInt(3, transaction.getProcessedBy());
            ps.setDate(4, transaction.getBorrowDate());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnBook(int transactionId, java.sql.Date returnDate) {
        String sql = "UPDATE transactions SET return_date = ?, status = 'returned' WHERE transaction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, returnDate);
            ps.setInt(2, transactionId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
