/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.model;

/**
 *
 * @author Enzo
 */
public class Category {
    
    private Integer id;
    private String category;
    private String description;
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getDescription() { return description; }
     public void setDescription(String description) { this.description = description; }
     
     public String getCategory() { return category; }
      public void setCategory(String category) { this.category = category; }
}
