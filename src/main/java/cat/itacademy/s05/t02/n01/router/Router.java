package cat.itacademy.s05.t02.n01.router;

import cat.itacademy.s05.t02.n01.handler.GameHandler;
import cat.itacademy.s05.t02.n01.handler.PlayerHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Router {
    private final static String PLAYER_PATH ="/player";
    private final static String GAME_PATH ="/game";
    
    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
    
    @Bean
    @RouterOperations
    public RouterFunction<ServerResponse> appRouter(PlayerHandler playerHandler, GameHandler gameHandler) {
        return RouterFunctions.route()
                .GET(PLAYER_PATH +"/ranking", playerHandler::ranking)
                .PUT(PLAYER_PATH +"/editnickname/{id}", playerHandler::changeNickname)
                .POST(GAME_PATH+"/create", gameHandler::createGame)
                .GET(GAME_PATH +"/{id}", gameHandler::getById)
                .POST(GAME_PATH+"/{id}/play", gameHandler::play)
                .DELETE(GAME_PATH + "/delete/{id}", gameHandler::delete)
                .build();
    }
    
}
