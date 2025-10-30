package com.levelup.catalogo_producto.service;

import com.levelup.catalogo_producto.dto.CategoryDTO;
import com.levelup.catalogo_producto.dto.CreateCategoryDTO;
import com.levelup.catalogo_producto.model.Category;
import com.levelup.catalogo_producto.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Conversión Model -> DTO
    private CategoryDTO convertToDTO(Category model) {
        return new CategoryDTO(
            model.getId(),
            model.getName(),
            model.getDescription()
        );
    }
    
    // Conversión DTO -> Model
    private Category convertToModel(CreateCategoryDTO dto) {
        Category model = new Category();
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        return model;
    }
    
    @Transactional
    public CategoryDTO createCategory(CreateCategoryDTO dto) {
        Category model = convertToModel(dto);
        Category saved = categoryRepository.save(model);
        return convertToDTO(saved);
    }
    
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public CategoryDTO getCategoryById(Long id) {
        Category model = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return convertToDTO(model);
    }
    
    @Transactional
    public CategoryDTO updateCategory(Long id, CreateCategoryDTO dto) {
        Category model = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        Category updated = categoryRepository.save(model);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
