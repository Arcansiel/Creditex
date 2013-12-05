package org.kofi.creditex.repository;

import org.kofi.creditex.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product, Integer>, QueryDslPredicateExecutor<Product> {
}
