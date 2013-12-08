package org.kofi.creditex.repository;

import org.kofi.creditex.model.ProlongationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 08.12.13
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public interface ProlongationApplicationRepository extends JpaRepository<ProlongationApplication, Integer>, QueryDslPredicateExecutor<ProlongationApplication> {
    List<ProlongationApplication> findByClient_Username(String username);
}
