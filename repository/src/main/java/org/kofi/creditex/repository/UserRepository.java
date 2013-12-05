package org.kofi.creditex.repository;

import org.kofi.creditex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, QueryDslPredicateExecutor<User> {
    User findByUsername(String username);
}
