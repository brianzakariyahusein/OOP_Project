/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Brian
 */
public class MemberModel {

    private int memberId;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private int createdBy;

    public MemberModel() {
    }

    public MemberModel(int memberId, String fullName, String address, String phone, String email, int createdBy) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy;
    }

    public MemberModel(String fullName, String address, String phone, String email, int createdBy) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdBy = createdBy;
    }

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
}
