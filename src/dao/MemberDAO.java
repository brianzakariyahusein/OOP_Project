package dao;

import config.DatabaseConnection;
import model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection conn;

    public MemberDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public void addMember(Member member) {
        String query = "INSERT INTO members (user_id, name, email, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, member.getUserId());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getPhone());
            stmt.setString(5, member.getAddress());
            
            stmt.executeUpdate();
            System.out.println("Member baru berhasil ditambahkan secara aman!");
        } catch (SQLException e) {
            System.out.println("Gagal tambah member: " + e.getMessage());
        }
    }

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM members";
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setUserId(rs.getInt("user_id"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setPhone(rs.getString("phone"));
                member.setAddress(rs.getString("address"));
                members.add(member);
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data member: " + e.getMessage());
        }
        return members;
    }
}