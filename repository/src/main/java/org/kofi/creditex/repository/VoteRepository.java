package org.kofi.creditex.repository;

import org.kofi.creditex.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface VoteRepository extends JpaRepository<Vote, Integer>, QueryDslPredicateExecutor<Vote> {
}
