package controller;

import dao.MemberDAO;
import dao.UserDAO;
import model.Member;
import model.User;
import java.util.List;

public class MemberController {
    private MemberDAO memberDAO;
    private UserDAO userDAO;

    public MemberController() {
        this.memberDAO = new MemberDAO();
        this.userDAO = new UserDAO();
    }

    // Logika pendaftaran member yang hanya bisa diakses via dashboard staf
    public void registerNewMember(String username, String name, String email, String phone, String address) {
        if (name.trim().isEmpty() || phone.trim().isEmpty() || username.trim().isEmpty()) {
            System.out.println("Gagal: Nama, username, dan nomor telepon wajib diisi.");
            return;
        }

        // Keamanan: Password bawaan diset menggunakan nomor telepon
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(phone); 
        newUser.setRole("MEMBER");

        // Simpan user ke database dan ambil ID nya
        int newUserId = userDAO.addUserReturnId(newUser);

        if (newUserId != -1) {
            Member newMember = new Member(0, newUserId, name, email, phone, address);
            memberDAO.addMember(newMember);
            System.out.println("Member berhasil didaftarkan. Silakan login menggunakan nomor telepon sebagai password awal.");
        } else {
            System.out.println("Pendaftaran dibatalkan karena username mungkin sudah dipakai.");
        }
    }
}