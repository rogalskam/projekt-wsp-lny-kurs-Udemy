package com.example.management_products.mediator;

import com.example.management_products.exceptions.ObjectExistInDBException;
import com.example.management_products.entity.CategoryDTO;
import com.example.management_products.services.CategoryService;
import com.example.management_products.translator.CategoryToCategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMediator {
    private final CategoryService categoryService;
    private final CategoryToCategoryDTO categoryToCategoryDTO;

    public ResponseEntity<List<CategoryDTO>> getCategory() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryService.getCategory().forEach(value -> {
                categoryDTOS.add(categoryToCategoryDTO.toCategoryDTO(value));
        });

        return ResponseEntity.ok().body(categoryDTOS);
    }

    public void createCategory(CategoryDTO categoryDTO) throws ObjectExistInDBException {
        categoryService.createCategory(categoryDTO);
    }
}
