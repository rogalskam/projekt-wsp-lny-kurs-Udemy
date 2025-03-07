package com.example.management_products.services;

import com.example.management_products.entity.ProductEntity;
import com.example.management_products.repository.CategoryRepository;
import com.example.management_products.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Value("${file-service.uri}")
    private String FILE_SERVICE;
    // odpowiedziamy za połączenie z bazą danych i wykonywanie rządań
    @PersistenceContext
    EntityManager entityManager;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public long countActiveProducts(String name, String category, Float price_min, Float price_max) {
        // zwraca listę obiektów od 1+
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // tworzenie zapytania z wskazanym modelem
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<ProductEntity> root = query.from(ProductEntity.class);

        List<Predicate> predicates = prepareQuery(name, category, price_min, price_max, criteriaBuilder, root);

        query.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    public List<ProductEntity> getProduct(String name, String category, Float price_min, Float price_max, String data, int page, int limit, String sort, String order) {
        //mechanizm budowania mechanizmu filtrowania
        // zwraca listę obiektów od 1+
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // tworzenie zapytania z wskazanym modelem
        CriteriaQuery<ProductEntity> query = criteriaBuilder.createQuery(ProductEntity.class);
        // root pobiera nazwy pól
        Root<ProductEntity> root = query.from(ProductEntity.class);
        // warunki
        if (data != null && !data.equals("") && name != null && !name.trim().equals("")) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(data, inputFormatter);
            return productRepository.findByNameAndCreateAt(name, date);
        }

        if (page <= 0 ) page = 1;

        // lista przechowująca warunki rządania
        List<Predicate> predicates = prepareQuery(name, category, price_min, price_max, criteriaBuilder, root);

        // sortowanie
        if(!order.isEmpty() && !sort.isEmpty()) {
            String column = null;
            switch (sort){
                case "name":
                    column="name";
                    break;
                case "category":
                    column = "category";
                    break;
                case "data":
                    column = "createAt";
                    break;
                default:
                    column="price";
                    break;
            }
            Order orderQuery;
            if (order.equals("desc")){
                orderQuery =  criteriaBuilder.desc(root.get(column));
            }else {
                orderQuery =  criteriaBuilder.asc(root.get(column));
            }
            query.orderBy(orderQuery);
        }

        // przekazanie wszystkich zapytań (ustawienie aby wiedzieć jaki typ tablica)
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).setFirstResult((page - 1) * limit).setMaxResults(limit).getResultList();
    }

    private List<Predicate> prepareQuery(
            String name,
            String category,
            Float price_min,
            Float price_max,
            CriteriaBuilder criteriaBuilder,
            Root<ProductEntity> root
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.trim().equals("")) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (category != null && !category.equals("")) {
            categoryRepository.findByShortId(category).
                    ifPresent(value -> predicates.add(criteriaBuilder.equal(root.get("category"), value)));
        }
        if (price_min != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("price"), price_min - 0.01)); // -0.01 wyliczenie wartości tą którą ktoś podał
        }
        if (price_max != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("price"), price_max + 0.01));
        }

        return predicates;
    }

    @Transactional
    public void createProduct(ProductEntity product) {
        if (product != null){
            product.setCreateAt(LocalDate.now());
            product.setUid(UUID.randomUUID().toString());
            product.setActivate(true);
            productRepository.save(product);
            for(String uuid: product.getImageUrls()) {
                activateImage(uuid);
            }
            return;
        }
        throw new RuntimeException();
    }

    private void activateImage(String uuid) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(FILE_SERVICE+"?uuid="+uuid))
                .method("PATCH",HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void delete(String uuid) throws RuntimeException {
        productRepository.findByUuid(uuid).ifPresentOrElse(value -> {
            for (String image:value.getImageUrls()) {
                deleteImages(image);
            }
            value.setActivate(false);
            productRepository.save(value);
        }, () -> {
            throw new RuntimeException();
        });

    }

    private void deleteImages(String uuid) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(FILE_SERVICE+"?uuid="+uuid);
    }
}
