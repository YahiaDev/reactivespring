package com.reactivespring.service;

import com.reactivespring.document.Team;
import com.reactivespring.exception.ErrorEnum;
import com.reactivespring.exception.TeamException;
import com.reactivespring.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Flux<Team> findAll() {
        return this.teamRepository.findAll();
    }

    public Mono<Team> save(Team team) {
        return this.findByName(team.getName())
                .filter(Objects::nonNull)
                .flatMap(t -> {
                    var message = String.format("Team %s is already exists", team.getName());
                    log.error(message);
                    return Mono.error(new TeamException(ErrorEnum.TEAM_EXISTS_ERROR));
                })
                .switchIfEmpty(this.teamRepository.save(team)).cast(Team.class);
    }

    public Mono<Team> findByName(String teamName) {
        return this.teamRepository.findByName(teamName);
    }

}
