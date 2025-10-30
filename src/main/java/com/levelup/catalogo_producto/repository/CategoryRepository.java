package com.levelup.catalogo_producto.repository;

import com.levelup.catalogo_producto.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Query nativa SQL
    @Query(value = "SELECT * FROM categories WHERE name = ?1", nativeQuery = true)
    Optional<Category> findByNameNative(String name);
    
    // Query objetal JPQL
    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Optional<Category> findByNameJPQL(String name);
}
