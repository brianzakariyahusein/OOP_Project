package controller;

import dao.BorrowingDAO;
import dao.BookDAO;
import model.Borrowing;
import java.sql.Date;
import java.util.List;

public class BorrowingController {
    private BorrowingDAO borrowingDAO;
    private BookDAO bookDAO;

    public BorrowingController() {
        this.borrowingDAO = new BorrowingDAO();
        this.bookDAO = new BookDAO();
    }

    // Fungsi untuk member melakukan request buku
    public void requestBorrowing(int memberId, int bookId, Date borrowDate, Date returnDate) {
        // Cek limit maksimal 3 buku
        int activeCount = borrowingDAO.countActiveBorrowings(memberId);
        if (activeCount >= 3) {
            System.out.println("Ditolak: Kamu sudah mencapai batas maksimal 3 buku (sedang dipinjam atau proses request).");
            return;
        }

        // Cek stok real time di database
        int currentStock = bookDAO.getBookStock(bookId);
        if (currentStock <= 0) {
            System.out.println("Ditolak: Maaf, stok buku sedang kosong.");
            return;
        }

        // Masukkan data dengan status PENDING
        Borrowing newBorrowing = new Borrowing(0, memberId, bookId, borrowDate, returnDate, "PENDING");
        borrowingDAO.addBorrowing(newBorrowing);
        System.out.println("Request pinjaman terkirim. Menunggu persetujuan staf.");
    }

    // Fungsi khusus staf untuk menyetujui peminjaman
    public void approveBorrowing(int borrowingId) {
        Borrowing b = borrowingDAO.getBorrowingById(borrowingId);
        if (b != null && b.getStatus().equals("PENDING")) {
            // Cek ulang stok memastikan buku belum habis dipinjam orang lain
            if (bookDAO.getBookStock(b.getBookId()) > 0) {
                borrowingDAO.updateBorrowingStatus(borrowingId, "BORROWED");
                bookDAO.updateStock(b.getBookId(), -1); // Potong stok 1
                System.out.println("Peminjaman disetujui, stok buku telah dikurangi.");
            } else {
                System.out.println("Gagal menyetujui, stok buku sudah habis direbut member lain.");
            }
        }
    }

    // Fungsi untuk mengembalikan buku dan menghitung denda otomatis
    public void returnBook(int borrowingId, Date actualReturnDate) {
        Borrowing b = borrowingDAO.getBorrowingById(borrowingId);
        if (b == null || !b.getStatus().equals("BORROWED")) {
            System.out.println("Data peminjaman tidak valid.");
            return;
        }

        long diffInMillis = actualReturnDate.getTime() - b.getReturnDate().getTime();
        long daysLate = diffInMillis / (1000 * 60 * 60 * 24);

        if (daysLate > 0) {
            // Asumsi denda Rp5.000 per hari
            long totalFine = daysLate * 5000;
            System.out.println("Buku terlambat dikembalikan selama " + daysLate + " hari. Denda: Rp" + totalFine);
            borrowingDAO.updateBorrowingStatus(borrowingId, "LATE");
        } else {
            System.out.println("Buku dikembalikan tepat waktu.");
            borrowingDAO.updateBorrowingStatus(borrowingId, "RETURNED");
        }
        
        // Kembalikan stok buku
        bookDAO.updateStock(b.getBookId(), 1);
    }
    
    public List<Borrowing> getMemberBorrowings(int memberId) {
        return borrowingDAO.getBorrowingsByMember(memberId);
    }
}