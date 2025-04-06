package com.Raj.repository;

import com.Raj.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    Category findByCategoryId(String categoryId);
}
