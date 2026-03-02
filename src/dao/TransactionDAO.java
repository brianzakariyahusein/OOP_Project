package dao;

import config.DatabaseConnection;
import model.TransactionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<TransactionModel> getAllTransactions() {
        List<TransactionModel> list = new ArrayList<>();
        String sql = "SELECT t.*, m.full_name, b.title, u.username "
                + "FROM transactions t "
                + "JOIN members m ON t.member_id = m.member_id "
                + "JOIN books b ON t.book_id = b.book_id "
                + "JOIN users u ON t.processed_by = u.user_id "
                + "ORDER BY t.transaction_id DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TransactionModel trans = new TransactionModel(
                        rs.getInt("transaction_id"),
                        rs.getInt("member_id"),
                        rs.getInt("book_id"),
                        rs.getInt("processed_by"),
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date"),
                        rs.getString("status")
                );
                trans.setMemberName(rs.getString("full_name"));
                trans.setBookTitle(rs.getString("title"));
                trans.setProcessedByName(rs.getString("username"));
                list.add(trans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean borrowBook(TransactionModel trans) {
        String sqlInsert = "INSERT INTO transactions (member_id, book_id, processed_by, borrow_date, status) VALUES (?, ?, ?, CURDATE(), 'borrowed')";
        String sqlUpdate = "UPDATE books SET stock = stock - 1 WHERE book_id = ? AND stock > 0";

        try (Connection conn = config.DatabaseConnection.getConnection()) {
            // 1. Masukin data transaksi
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, trans.getMemberId());
                psInsert.setInt(2, trans.getBookId());
                psInsert.setInt(3, trans.getProcessedBy());
                int rowsTrans = psInsert.executeUpdate();

                if (rowsTrans > 0) {
                    // 2. Langsung hajar update stok tanpa commit-commit-an dulu buat ngetes
                    try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                        psUpdate.setInt(1, trans.getBookId());
                        int rowsStock = psUpdate.executeUpdate();

                        if (rowsStock > 0) {
                            return true; // Berhasil keduanya
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean returnBook(int transactionId) {
        Connection conn = null;
        try {
            conn = config.DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Cek dulu ID buku yang dipinjam dan pastikan statusnya masih borrowed
            String cekSql = "SELECT book_id, status FROM transactions WHERE transaction_id = ?";
            int bookId = -1;
            String status = "";

            try (PreparedStatement psCek = conn.prepareStatement(cekSql)) {
                psCek.setInt(1, transactionId);
                try (ResultSet rs = psCek.executeQuery()) {
                    if (rs.next()) {
                        bookId = rs.getInt("book_id");
                        status = rs.getString("status");
                    }
                }
            }

            // Kalau datanya tidak ketemu atau sudah dikembalikan, batalkan proses
            if (bookId == -1 || "returned".equals(status)) {
                return false;
            }

            // Ubah status dan catat tanggal kembali
            String updateTransSql = "UPDATE transactions SET return_date = CURDATE(), status = 'returned' WHERE transaction_id = ?";
            try (PreparedStatement psUpdateTrans = conn.prepareStatement(updateTransSql)) {
                psUpdateTrans.setInt(1, transactionId);
                psUpdateTrans.executeUpdate();
            }

            // Tambah lagi stok bukunya
            String updateBookSql = "UPDATE books SET stock = stock + 1 WHERE book_id = ?";
            try (PreparedStatement psUpdateBook = conn.prepareStatement(updateBookSql)) {
                psUpdateBook.setInt(1, bookId);
                psUpdateBook.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (java.sql.SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
