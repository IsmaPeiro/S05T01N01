package cat.itacademy.s05.t02.n01.service;

import cat.itacademy.s05.t02.n01.dto.GameDto;
import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.entity.Game;
import cat.itacademy.s05.t02.n01.entity.Player;
import cat.itacademy.s05.t02.n01.exception.CustomException;
import cat.itacademy.s05.t02.n01.mapper.GameMapper;
import cat.itacademy.s05.t02.n01.mapper.PlayerMapper;
import cat.itacademy.s05.t02.n01.repository.GameRepository;
import cat.itacademy.s05.t02.n01.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameService {
    private final static String NF_MESSAGE = "Game not found";
    
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    
    public Mono<GameDto> getById(String id) {
        return gameRepository.findById(id).map(gameMapper::toDto)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }
    
    public Mono<GameDto> createGame(String nickname) {
        Mono<Player> playerMono = playerRepository.findByNickname(nickname);
        return playerMono.switchIfEmpty(Mono.just(Player.builder().nickname(nickname).build())
                        .flatMap(playerRepository::save))
                .flatMap(player -> {
                    Game game = new Game(playerMapper.toDto(player));
                    game.startGame();
                    return checkEndGame(game).then(gameRepository.save(game))
                            .map(gameMapper::toDto);
                });
        
    }
    
    public Mono<GameDto> play(String id, String action) {
        Mono<Game> game = gameRepository.findById(id);
        return game.switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)))
                .flatMap(g -> {
                    if (!g.getStatus().equals(Game.Status.ON_PROGRESS)) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "The game is over"));
                    } else if (!action.equals("HIT") && !action.equals("STAND")) {
                        return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Action no valid"));
                    }
                    g.makeAMove(Game.Actions.valueOf(action));
                    return checkEndGame(g).then(gameRepository.save(g))
                            .map(gameMapper::toDto);
                });
    }
    
    public Mono<PlayerDto> checkEndGame(Game game) {
        return game.getStatus() == Game.Status.ON_PROGRESS
                ? Mono.empty()
                : updatePlayersInGames(game.getPlayer()).then(playerRepository.save(playerMapper.toEntity(game.getPlayer())))
                .map(playerMapper::toDto);
    }
    
    private Flux<GameDto> updatePlayersInGames(PlayerDto player) {
        return gameRepository.findAll().filter(game -> game.getPlayer().getNickname().equals(player.getNickname()))
                .flatMap(game -> {
                    int score=playerMapper.toEntity(player).calculateScore();
                    player.setScore(score);
                    game.setPlayer(player);
                    return gameRepository.save(game)
                            .map(gameMapper::toDto);
                });
    }
    
    public Mono<Void> delete(String id) {
        Mono<Boolean> gameId = gameRepository.findById(id).hasElement();
        return gameId.flatMap(exists -> exists
                ? gameRepository.deleteById(id)
                : Mono.error(new CustomException(HttpStatus.NOT_FOUND, NF_MESSAGE)));
    }
}