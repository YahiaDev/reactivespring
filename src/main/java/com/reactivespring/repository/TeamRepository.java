package com.reactivespring.repository;

import com.reactivespring.document.Team;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TeamRepository extends ReactiveCrudRepository<Team, String> {

    Mono<Team> findByName(String name);
}
