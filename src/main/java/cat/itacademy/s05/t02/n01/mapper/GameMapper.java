package cat.itacademy.s05.t02.n01.mapper;

import cat.itacademy.s05.t02.n01.dto.GameDto;
import cat.itacademy.s05.t02.n01.entity.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDto toDto (Game game);
    Game toEntity (GameDto gameDto);
}
