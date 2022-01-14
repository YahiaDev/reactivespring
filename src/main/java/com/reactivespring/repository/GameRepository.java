package com.reactivespring.repository;

import com.reactivespring.document.Game;
import com.reactivespring.document.Team;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, String> {

    Mono<Game> findByTeamsInAndDateEquals(List<Team> teams, LocalDateTime date);

    Mono<Game> findByDateEquals(LocalDateTime date);

}
