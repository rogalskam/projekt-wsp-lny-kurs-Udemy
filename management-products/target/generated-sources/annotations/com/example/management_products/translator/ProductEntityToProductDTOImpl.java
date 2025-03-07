package com.example.management_products.translator;

import com.example.management_products.entity.Category;
import com.example.management_products.entity.CategoryDTO;
import com.example.management_products.entity.ProductDTO;
import com.example.management_products.entity.ProductEntity;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-07T14:12:22+0100",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ProductEntityToProductDTOImpl extends ProductEntityToProductDTO {

    @Override
    protected ProductDTO toDTO(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setUid( productEntity.getUid() );
        productDTO.setActivate( productEntity.isActivate() );
        productDTO.setName( productEntity.getName() );
        productDTO.setMainDesc( productEntity.getMainDesc() );
        productDTO.setDescHtml( productEntity.getDescHtml() );
        productDTO.setPrice( productEntity.getPrice() );
        String[] imageUrls = productEntity.getImageUrls();
        if ( imageUrls != null ) {
            productDTO.setImageUrls( Arrays.copyOf( imageUrls, imageUrls.length ) );
        }
        productDTO.setParameters( productEntity.getParameters() );
        productDTO.setCreateAt( productEntity.getCreateAt() );

        productDTO.setCategoryDTO( toCategoryDTO(productEntity.getCategory()) );

        return productDTO;
    }

    @Override
    protected CategoryDTO toCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName( category.getName() );
        categoryDTO.setShortId( category.getShortId() );

        return categoryDTO;
    }
}
