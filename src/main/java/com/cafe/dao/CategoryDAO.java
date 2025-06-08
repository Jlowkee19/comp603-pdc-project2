/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cafe.dao;

import com.cafe.model.Category;
import java.util.List;


/**
 *
 * @author Enzo
 */
public interface CategoryDAO {
        List<Category> getCategoriesOfTheDay();
        Category getCategoryById(Integer id);
}
