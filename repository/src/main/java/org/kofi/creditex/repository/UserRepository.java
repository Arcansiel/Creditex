package org.kofi.creditex.repository;

import org.kofi.creditex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
    User findByUsername(String username);
    User findByUserData_FirstAndUserData_LastAndUserData_PatronymicAndUserData_PassportSeriesAndUserData_PassportNumber(String userData_First, String userData_Last, String userData_Patronymic, String userData_Series, int userData_Number);
}
