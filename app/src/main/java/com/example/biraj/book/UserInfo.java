package com.example.biraj.book;

/**
 * Created by Biraj on 7/14/2017.
 */

public class UserInfo{
    private String id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String contact;

    @Override
    public String toString() {
        return id + " - " + name + " - " + email + " - " + password + " - " + address + " - " + contact;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
