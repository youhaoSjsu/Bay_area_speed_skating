package com.example.student_portal.module;

public class MtUser {
    private int user_id;
    private String username;
    //private Integer age;
    private String email;
    private String phone;
    private String zip;
    private String password;
    private double balance;

    private int role;

    public MtUser(int user_id, String username, String email, String phone, String zip, String password, double balance,int role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.zip = zip;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public MtUser() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
