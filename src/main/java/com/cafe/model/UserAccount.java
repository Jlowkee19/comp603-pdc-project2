package com.cafe.model;


import java.io.Serializable;
import java.util.Arrays;

public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private byte[] photo;
    private Role role;

    // No-argument constructor
    public UserAccount() {
    }

    // Parameterized constructor
    public UserAccount(Long id, String firstname, String lastname, String username, String password,
                       Boolean active, byte[] photo, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.role = role;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String surname) { this.lastname = surname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte[] photo) { this.photo = photo; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    // toString() method (excludes password and photo for safety)

    @Override
    public String toString() {
        return "UserAccount {" +
                "id = " + id +
                ", firstname = '" + firstname + '\'' +
                ", surname = '" + lastname + '\'' +
                ", username = '" + username + '\'' +
                ", role = " + (role != null ? role.getName() : "null") +
                '}';
    }
}