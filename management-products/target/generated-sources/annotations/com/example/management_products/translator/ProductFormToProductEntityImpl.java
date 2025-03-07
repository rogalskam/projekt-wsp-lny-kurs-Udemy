package com.example.management_products.translator;

import com.example.management_products.entity.Category;
import com.example.management_products.entity.ProductEntity;
import com.example.management_products.entity.ProductFormDTO;
import java.time.LocalDate;
import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-07T14:12:22+0100",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ProductFormToProductEntityImpl extends ProductFormToProductEntity {

    @Override
    protected ProductEntity translate(ProductFormDTO productFormDTO) {
        if ( productFormDTO == null ) {
            return null;
        }

        String[] imageUrls = null;
        String name = null;
        String mainDesc = null;
        String descHtml = null;
        float price = 0.0f;
        String parameters = null;

        String[] imageUrls1 = productFormDTO.getImagesUuid();
        if ( imageUrls1 != null ) {
            imageUrls = Arrays.copyOf( imageUrls1, imageUrls1.length );
        }
        name = productFormDTO.getName();
        mainDesc = productFormDTO.getMainDesc();
        descHtml = productFormDTO.getDescHtml();
        price = productFormDTO.getPrice();
        parameters = productFormDTO.getParameters();

        Category category = translate(productFormDTO.getCategory());
        long id = 0L;
        String uid = null;
        boolean activate = false;
        LocalDate createAt = null;

        ProductEntity productEntity = new ProductEntity( id, uid, activate, name, mainDesc, descHtml, price, imageUrls, parameters, createAt, category );

        return productEntity;
    }
}
