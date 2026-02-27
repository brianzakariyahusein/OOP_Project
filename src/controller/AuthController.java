package controller;

import dao.UserDAO;
import model.User;

public class AuthController {
    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Keamanan Controller: Username dan password tidak boleh kosong!");
            return null;
        }

        User user = userDAO.login(username, password);
        
        if (user != null) {
            System.out.println("Login berhasil! Selamat datang, " + user.getUsername());
            System.out.println("Mengarahkan ke dashboard untuk role: " + user.getRole());
        } else {
            System.out.println("Login gagal! Username atau password tidak terdaftar.");
        }
        
        return user;
    }
    
    public void changeUserRole(int userId, String newRole) {
        if (newRole.equals("ADMIN") || newRole.equals("STAFF") || newRole.equals("MEMBER")) {
            userDAO.updateRole(userId, newRole);
            System.out.println("Role berhasil diubah menjadi " + newRole);
        } else {
            System.out.println("Role tidak valid.");
        }
    }

    public void changePassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Validasi Gagal: Konfirmasi password tidak cocok.");
            return;
        }
        if (newPassword.length() < 6) {
            System.out.println("Validasi Gagal: Password baru minimal 6 karakter.");
            return;
        }
        userDAO.changePassword(userId, newPassword);
        System.out.println("Password berhasil diperbarui.");
    }
}