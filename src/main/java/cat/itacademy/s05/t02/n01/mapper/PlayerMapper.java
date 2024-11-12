package cat.itacademy.s05.t02.n01.mapper;

import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    @Mapping(target="score", expression = "java(player.calculateScore())")
    PlayerDto toDto (Player player);
    Player toEntity (PlayerDto playerDto);
}
