/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.MemberModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brian
 */
public class MemberDAO {

    public List<MemberModel> getAllMembers() {
        List<MemberModel> list = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = config.DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new MemberModel(
                        rs.getInt("member_id"),
                        rs.getString("full_name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("created_by"),
                        rs.getTimestamp("created_at") // Ambil datanya di sini
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertMember(MemberModel member) {
        String sql = "INSERT INTO members (full_name, address, phone, email, created_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getFullName());
            ps.setString(2, member.getAddress());
            ps.setString(3, member.getPhone());
            ps.setString(4, member.getEmail());
            ps.setInt(5, member.getCreatedBy());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMember(MemberModel member) {
        String sql = "UPDATE members SET full_name=?, address=?, phone=?, email=?, created_by=? WHERE member_id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getFullName());
            ps.setString(2, member.getAddress());
            ps.setString(3, member.getPhone());
            ps.setString(4, member.getEmail());
            ps.setInt(5, member.getCreatedBy());
            ps.setInt(6, member.getMemberId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
