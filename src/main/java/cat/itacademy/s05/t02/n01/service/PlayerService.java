package cat.itacademy.s05.t02.n01.service;

import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.entity.Player;
import cat.itacademy.s05.t02.n01.exception.CustomException;
import cat.itacademy.s05.t02.n01.mapper.PlayerMapper;
import cat.itacademy.s05.t02.n01.repository.GameRepository;
import cat.itacademy.s05.t02.n01.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final static String NF_MESSAGE = "player not found";
    private final static String NAME_MESSAGE = "player already exists";
    
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final GameRepository gameRepository;
    
    public Mono<PlayerDto> changeNickname(Long id, String newNickname) {
        Mono<Player> playerById = playerRepository.findById(id);
        Mono<Boolean> playerRepeatedNickname = playerRepository.repeatedNickname(id, newNickname).hasElement();
        return playerById.hasElement().flatMap(
                        existsId -> existsId
                                ? playerRepeatedNickname.flatMap(existsNickname -> existsNickname
                                ? Mono.error(new CustomException(HttpStatus.BAD_REQUEST, NAME_MESSAGE))
                                : playerById.flatMap(player -> gameRepository.findAll().filter(game -> game.getPlayer().getNickname().equals(player.getNickname()))
                                        .flatMap(game -> {
                                            game.getPlayer().setNickname(newNickname);
                                            return gameRepository.save(game);
                                        }).then(Mono.fromCallable(() -> {
                                            player.setNickname(newNickname);
                                            return player;
                                        }))
                                        .flatMap(playerRepository::save)))
                                : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)))
                .map(playerMapper::toDto);
    }
    
    public Flux<PlayerDto> ranking() {
        return playerRepository.findAll().map(playerMapper::toDto).sort(Comparator.comparing(PlayerDto::getScore).reversed())
                .switchIfEmpty(Flux.error(new CustomException(HttpStatus.BAD_REQUEST, NF_MESSAGE)));
    }
}
