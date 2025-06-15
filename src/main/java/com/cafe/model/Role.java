package com.cafe.model;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */


public class Role {
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // toString()
    @Override
    public String toString() {
        return "Role {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                '}';
    }
}