package com.sagar.electronic.store.repositories;

import com.sagar.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String>
{
}
