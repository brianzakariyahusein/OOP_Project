package dao;

import config.DatabaseConnection;
import model.Borrowing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;




public class BorrowingDAO {
    private Connection conn;

    public BorrowingDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public void addBorrowing(Borrowing borrowing) {
        String query = "INSERT INTO borrowings (member_id, book_id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, borrowing.getMemberId());
            stmt.setInt(2, borrowing.getBookId());
            stmt.setDate(3, borrowing.getBorrowDate());
            stmt.setDate(4, borrowing.getReturnDate());
            stmt.setString(5, borrowing.getStatus());
            
            stmt.executeUpdate();
            System.out.println("Data peminjaman berhasil dicatat ke database!");
        } catch (SQLException e) {
            System.out.println("Gagal mencatat peminjaman: " + e.getMessage());
        }
    }

    public void updateBorrowingStatus(int id, String newStatus) {
        String query = "UPDATE borrowings SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            System.out.println("Status peminjaman sukses diperbarui!");
        } catch (SQLException e) {
            System.out.println("Gagal update status: " + e.getMessage());
        }
    }
    
    // Mengecek berapa buku yang sedang dipinjam atau direquest oleh member
    public int countActiveBorrowings(int memberId) {
        String query = "SELECT COUNT(*) AS total FROM borrowings WHERE member_id = ? AND status IN ('PENDING', 'BORROWED')";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            System.out.println("Gagal menghitung limit peminjaman: " + e.getMessage());
        }
        return 0;
    }

    // Mengambil satu data peminjaman spesifik untuk cek denda
    public Borrowing getBorrowingById(int id) {
        String query = "SELECT * FROM borrowings WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Borrowing(rs.getInt("id"), rs.getInt("member_id"), rs.getInt("book_id"), 
                                     rs.getDate("borrow_date"), rs.getDate("return_date"), rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data peminjaman: " + e.getMessage());
        }
        return null;
    }
    
    public List<Borrowing> getBorrowingsByMember(int memberId) {
        List<Borrowing> list = new ArrayList<>();
        String query = "SELECT * FROM borrowings WHERE member_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Borrowing(
                    rs.getInt("id"), rs.getInt("member_id"), rs.getInt("book_id"),
                    rs.getDate("borrow_date"), rs.getDate("return_date"), rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data riwayat pinjaman: " + e.getMessage());
        }
        return list;
    }
}