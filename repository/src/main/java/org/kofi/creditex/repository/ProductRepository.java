package org.kofi.creditex.repository;

import org.kofi.creditex.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslPredicateExecutor<Product> {
    List<Product> findByActive(boolean active);
}
