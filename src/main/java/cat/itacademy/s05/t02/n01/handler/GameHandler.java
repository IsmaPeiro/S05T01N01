package cat.itacademy.s05.t02.n01.handler;

import cat.itacademy.s05.t02.n01.dto.GameDto;
import cat.itacademy.s05.t02.n01.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GameHandler {
    private final GameService gameService;
    
    public Mono<ServerResponse> createGame(ServerRequest request) {
        return request.bodyToMono(String.class)
                .flatMap(nickname ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(gameService.createGame(nickname), GameDto.class));
    }
    
   public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<GameDto> player = gameService.getById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(player, GameDto.class);
    }
    
    public Mono<ServerResponse> play(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(String.class)
                .flatMap(action ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(gameService.play(id, action), GameDto.class));
    }
    
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(gameService.delete(id), GameDto.class);
    }
}