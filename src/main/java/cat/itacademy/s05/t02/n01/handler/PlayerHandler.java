package cat.itacademy.s05.t02.n01.handler;

import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PlayerHandler {
    private final PlayerService playerService;
    
    public Mono<ServerResponse> changeNickname(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(String.class)
                .flatMap(newNickName ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Message", "Nickname changed successfully")
                                .body(playerService.changeNickname(id, newNickName), PlayerDto.class)
                );
    }
    
    public Mono<ServerResponse> ranking(ServerRequest request) {
        Flux<PlayerDto> products = playerService.ranking();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .header("Message", "Request completed successfully")
                .body(products, PlayerDto.class);
    }
}
