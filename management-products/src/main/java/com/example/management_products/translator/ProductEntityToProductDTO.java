package com.example.management_products.translator;

import com.example.management_products.entity.Category;
import com.example.management_products.entity.CategoryDTO;
import com.example.management_products.entity.ProductDTO;
import com.example.management_products.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class ProductEntityToProductDTO {
    public ProductDTO toProductDTO(ProductEntity productEntity) {
        return toDTO(productEntity);
    }

    @Mappings({
            @Mapping(expression = "java(toCategoryDTO(productEntity.getCategory()))", target = "categoryDTO")
    })
    protected abstract ProductDTO toDTO(ProductEntity productEntity);

    @Mappings({})
    protected abstract CategoryDTO toCategoryDTO(Category category);
}
