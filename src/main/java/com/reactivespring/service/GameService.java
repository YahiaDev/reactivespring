package com.reactivespring.service;

import com.reactivespring.document.Game;
import com.reactivespring.document.Team;
import com.reactivespring.exception.ErrorEnum;
import com.reactivespring.exception.GameException;
import com.reactivespring.exception.TeamException;
import com.reactivespring.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GameService {

    private final GameRepository gameRepository;
    private final TeamService teamService;

    public GameService(GameRepository gameRepository, TeamService teamService) {
        this.gameRepository = gameRepository;
        this.teamService = teamService;
    }

    public Flux<Game> findAll() {
        return this.gameRepository.findAll();
    }

    public Mono<Game> save(Game game) {
        Mono<Team> team1 = this.teamService.findByName(game.getTeams().get(0).getName());
        Mono<Team> team2 = this.teamService.findByName(game.getTeams().get(1).getName());

        return team1
                .filter(Objects::nonNull)
                .flatMap(t1 ->
                        team2.filter(Objects::nonNull)
                                .flatMap(t2 ->
                                        this.findByTeamsAndDate(List.of(t1, t2), game.getDate())
                                                .filter(Objects::nonNull)
                                                .flatMap(g -> Mono.error(new GameException(ErrorEnum.GAME_EXISTS_ERROR)).cast(Game.class))
                                                .switchIfEmpty(this.processSaveGame(game, List.of(t1, t2))))
                                .switchIfEmpty(Mono.error(new TeamException(ErrorEnum.TEAM_NOT_EXISTS_ERROR))))
                .switchIfEmpty(Mono.error(new TeamException(ErrorEnum.TEAM_NOT_EXISTS_ERROR)));

    }

    public Mono<Game> findByTeamsAndDate(List<Team> teams, LocalDateTime date) {
        return this.gameRepository.findByTeamsInAndDateEquals(teams, date);

    }

    private Mono<Game> processSaveGame(Game game, List<Team> teams) {
        log.info("save new game");
        game.setTeams(teams);
        return this.gameRepository.save(game);
    }


}
