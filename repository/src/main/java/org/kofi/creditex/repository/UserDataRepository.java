package org.kofi.creditex.repository;

import org.kofi.creditex.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserDataRepository extends JpaRepository<UserData, Long>, QueryDslPredicateExecutor<UserData> {
}
