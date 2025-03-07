package com.example.management_products.services;

import com.example.management_products.exceptions.ObjectExistInDBException;
import com.example.management_products.entity.Category;
import com.example.management_products.entity.CategoryDTO;
import com.example.management_products.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }
    public void createCategory(CategoryDTO categoryDTO) throws ObjectExistInDBException {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setShortId(UUID.randomUUID().toString().replace("-","").substring(0,12));

        categoryRepository.findByName(category.getName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Category exist in DB with this name");
        });

        categoryRepository.save(category);
    }

    public Optional<Category> findCategoryByShortID(String shortId) {
        return categoryRepository.findByShortId(shortId);
    }
}
