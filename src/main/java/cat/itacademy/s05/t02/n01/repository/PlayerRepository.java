package cat.itacademy.s05.t02.n01.repository;

import cat.itacademy.s05.t02.n01.entity.Player;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {
    Mono<Player> findByNickname (String nickname);
    
    @Query("SELECT * FROM players WHERE id <> :id AND nickname = :nickname")
    Mono<Player> repeatedNickname(Long id, String nickname);
}
