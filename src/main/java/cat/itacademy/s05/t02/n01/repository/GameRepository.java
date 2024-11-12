package cat.itacademy.s05.t02.n01.repository;

import cat.itacademy.s05.t02.n01.entity.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface GameRepository extends ReactiveCrudRepository<Game, String> {
}
