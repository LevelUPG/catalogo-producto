package com.levelup.catalogo_producto.service;

import com.levelup.catalogo_producto.dto.CreateProductDTO;
import com.levelup.catalogo_producto.dto.ProductDTO;
import com.levelup.catalogo_producto.model.Category;
import com.levelup.catalogo_producto.model.Product;
import com.levelup.catalogo_producto.repository.CategoryRepository;
import com.levelup.catalogo_producto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Conversión Model -> DTO
    private ProductDTO convertToDTO(Product model) {
        return new ProductDTO(
            model.getId(),
            model.getName(),
            model.getDescription(),
            model.getPrice(),
            model.getStock(),
            model.getCategory().getId(),
            model.getCategory().getName()
        );
    }
    
    // Conversión DTO -> Model
    private Product convertToModel(CreateProductDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoryId()));
        
        Product model = new Product();
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setPrice(dto.getPrice());
        model.setStock(dto.getStock());
        model.setCategory(category);
        return model;
    }
    
    @Transactional
    public ProductDTO createProduct(CreateProductDTO dto) {
        Product model = convertToModel(dto);
        Product saved = productRepository.save(model);
        return convertToDTO(saved);
    }
    
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public ProductDTO getProductById(Long id) {
        Product model = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return convertToDTO(model);
    }
    
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public ProductDTO updateProduct(Long id, CreateProductDTO dto) {
        Product model = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        Category category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoryId()));
        
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setPrice(dto.getPrice());
        model.setStock(dto.getStock());
        model.setCategory(category);
        
        Product updated = productRepository.save(model);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }
}
