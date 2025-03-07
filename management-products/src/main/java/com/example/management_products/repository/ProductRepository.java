package com.example.management_products.repository;

import com.example.management_products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT count(*) from products where activate is true")
    long countActiveProducts();

    List<ProductEntity> findByNameAndCreateAt(String name, LocalDate date);

    Optional<ProductEntity> findByUuid(String uuid);
}
