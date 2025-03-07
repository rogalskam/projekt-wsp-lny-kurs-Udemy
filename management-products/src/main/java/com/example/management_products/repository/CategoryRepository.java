package com.example.management_products.repository;

import com.example.management_products.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByShortId(String shortId);

    Optional<Category> findByName(String name);
}
