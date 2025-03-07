package com.example.management_products.fasada;

import com.example.management_products.entity.ProductFormDTO;
import com.example.management_products.entity.Response;
import com.example.management_products.mediator.ProductMediator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductMediator productMediator;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(
            HttpServletRequest request,
            //required false - wymagalność parametru
            @RequestParam(required = false) String name_like, // nazwa produktu do wyszukiwania (niedokładna nazwa)
            @RequestParam(required = false) String data, // data utowrzenia filtr
            @RequestParam(required = false) String _category, // kategoria (shortId do wszyukiwania)
            @RequestParam(required = false) Float price_min, // min cena
            @RequestParam(required = false) Float price_max, // max cena
            @RequestParam(required = false,defaultValue = "1") int _page, // paginacja
            @RequestParam(required = false,defaultValue = "10") int _limit,
            @RequestParam(required = false,defaultValue = "price") String _sort,
            @RequestParam(required = false,defaultValue = "asc") String _order
    ) {
        return productMediator.getProduct(_page,_limit,name_like,_category,price_min,price_max,data, _sort, _order);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> save(@RequestBody ProductFormDTO productFormDTO) {
        return productMediator.saveProduct(productFormDTO);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestBody String uuid) {
        return productMediator.deleteProduct(uuid);
    }
}
