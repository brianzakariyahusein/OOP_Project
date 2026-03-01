package model;

import java.sql.Timestamp;

public class MemberModel {

    private int memberId;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private int createdBy;
    private Timestamp createdAt;

    public MemberModel() {
    }

    // Konstruktor LENGKAP (Biasanya dipake pas ambil data dari database/DAO)
    public MemberModel(int memberId, String fullName, String address, String phone, String email, int createdBy, Timestamp createdAt) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy; // Tadi kamu tulis 'created' (salah)
        this.createdAt = createdAt;
    }

    // Konstruktor untuk EDIT (Tanpa Timestamp)
    public MemberModel(int memberId, String fullName, String address, String phone, String email, int createdBy) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy;
    }

    // Konstruktor untuk TAMBAH BARU (Tanpa ID dan Tanpa Timestamp)
    public MemberModel(String fullName, String address, String phone, String email, int createdBy) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy; // Tadi kamu tulis 'userId' (salah)
    }

    // Getter dan Setter tetap sama di bawah...
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
