package com.reactivespring.config;

import com.reactivespring.document.Game;
import com.reactivespring.document.Team;
import com.reactivespring.service.GameService;
import com.reactivespring.service.TeamService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ApiHttpConfig {

    private static final String GAME_URL = "/api/games";
    private static final String TEAM_URL = "/api/teams";

    @Bean
    public RouterFunction<ServerResponse> gameHttp(GameService gs) {
        return route()
                .GET(GAME_URL, request -> ServerResponse.ok().body(gs.findAll(), Game.class))
                .POST(GAME_URL, request ->
                        request.bodyToMono(Game.class)
                                .flatMap(gs::save)
                                .flatMap(game ->
                                        ServerResponse
                                                .created(URI.create(GAME_URL + "/" + game.getId()))
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(BodyInserters.fromValue(game)))
                )

                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> teamHttp(TeamService ts) {
        return route()
                .GET(TEAM_URL, request -> ServerResponse.ok().body(ts.findAll(), Team.class))
                .POST(TEAM_URL, request -> request.bodyToMono(Team.class)
                        .flatMap(ts::save)
                        .flatMap(team -> ServerResponse
                                .created(URI.create(TEAM_URL + "/" + team.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(team))))
                .build();
    }

}
