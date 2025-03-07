package com.example.management_products.translator;

import com.example.management_products.entity.Category;
import com.example.management_products.entity.CategoryDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-07T14:12:22+0100",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class CategoryToCategoryDTOImpl extends CategoryToCategoryDTO {

    @Override
    protected CategoryDTO translateToCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName( category.getName() );
        categoryDTO.setShortId( category.getShortId() );

        return categoryDTO;
    }
}
