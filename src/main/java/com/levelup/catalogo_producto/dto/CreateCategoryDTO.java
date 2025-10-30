package com.levelup.catalogo_producto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    
    private String description;
}
