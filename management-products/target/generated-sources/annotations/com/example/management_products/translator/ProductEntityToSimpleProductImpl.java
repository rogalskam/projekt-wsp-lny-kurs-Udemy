package com.example.management_products.translator;

import com.example.management_products.entity.ProductEntity;
import com.example.management_products.entity.SimpleProductDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-07T14:12:22+0100",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ProductEntityToSimpleProductImpl extends ProductEntityToSimpleProduct {

    @Override
    protected SimpleProductDTO toSimpleProductDTO(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        SimpleProductDTO simpleProductDTO = new SimpleProductDTO();

        simpleProductDTO.setName( productEntity.getName() );
        simpleProductDTO.setMainDesc( productEntity.getMainDesc() );
        simpleProductDTO.setPrice( productEntity.getPrice() );
        simpleProductDTO.setCreateAt( productEntity.getCreateAt() );

        simpleProductDTO.setImageUrl( getImageUrl(productEntity.getImageUrls()) );

        return simpleProductDTO;
    }
}
