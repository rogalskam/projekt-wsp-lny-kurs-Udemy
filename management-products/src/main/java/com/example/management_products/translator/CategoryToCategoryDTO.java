package com.example.management_products.translator;

import com.example.management_products.entity.Category;
import com.example.management_products.entity.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class CategoryToCategoryDTO {

    public CategoryDTO toCategoryDTO(Category category) {
        return translateToCategoryDTO(category);
    }

    @Mappings({})
    protected abstract CategoryDTO translateToCategoryDTO(Category category);
}
