package com.levelup.catalogo_producto.repository;

import com.levelup.catalogo_producto.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Query nativa SQL
    @Query(value = "SELECT * FROM products WHERE category_id = ?1", nativeQuery = true)
    List<Product> findByCategoryIdNative(Long categoryId);
    
    // Query objetal JPQL
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> findAvailableProducts();
}
