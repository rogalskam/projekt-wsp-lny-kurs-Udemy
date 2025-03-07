package com.example.management_products.mediator;

import com.example.management_products.entity.*;
import com.example.management_products.exceptions.CategoryDontExistException;
import com.example.management_products.services.CategoryService;
import com.example.management_products.services.ProductService;
import com.example.management_products.translator.ProductEntityToProductDTO;
import com.example.management_products.translator.ProductEntityToSimpleProduct;
import com.example.management_products.translator.ProductFormToProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMediator {
    private final ProductService productService;
    private final CategoryService categoryService;

    private final ProductEntityToSimpleProduct productEntityToSimpleProduct;
    private final ProductEntityToProductDTO productEntityToProductDTO;
    private final ProductFormToProductEntity formToProductEntity;

    @Value("${file-service.uri}")
    private String FILE_SERVICE;

    public ResponseEntity<?> getProduct(int page, int limit,String name,String category,Float price_min,Float price_max,String data, String sort, String order) {
        if (name != null && !name.isEmpty()) {
            try {
                name = URLDecoder.decode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        // wyszstkie produkty aktywne niezależnie od filtrów
        List<ProductEntity> product = productService.getProduct(name,category,price_min,price_max,data, page, limit, sort, order);

        // zmiana uuid na adres url zasobu
        product.forEach(value -> {
            for(int i = 0 ; i < value.getImageUrls().length; i++) {
                value.getImageUrls()[i] = FILE_SERVICE+"?uuid="+value.getImageUrls()[i];
            }
        });

        if(name == null || name.isEmpty() || data == null || data.isEmpty()) {
            List<SimpleProductDTO> simpleProductDTOS = new ArrayList<>();
            long totalCount = productService.countActiveProducts(name, category, price_min, price_max);
            product.forEach(value ->
                simpleProductDTOS.add(productEntityToSimpleProduct.toSimpleProduct(value))
            );

            return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(simpleProductDTOS);
        }
        ProductDTO productDTO = productEntityToProductDTO.toProductDTO(product.get(0));
        return ResponseEntity.ok().body(productDTO);

    }

    public ResponseEntity<Response> saveProduct(ProductFormDTO productFormDTO) {
        try{
            ProductEntity product = formToProductEntity.toProductEntity(productFormDTO);
            categoryService.findCategoryByShortID(product.getCategory().getShortId()).ifPresentOrElse(product::setCategory,()->{
                throw new CategoryDontExistException();
            });
            productService.createProduct(product);
            return ResponseEntity.ok(new Response("Successful created a product"));
        }catch (RuntimeException e){
            return ResponseEntity.status(400).body(new Response("Can't create product category don't exist"));
        }

    }

    public ResponseEntity<Response> deleteProduct(String uuid) {
        try {
            productService.delete(uuid);
            return ResponseEntity.ok(new Response("Successful delete product"));
        } catch(RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(new Response("Product don't exist"));
        }
    }
}
