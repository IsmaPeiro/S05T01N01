package cat.itacademy.s05.t02.n01.dto;

import cat.itacademy.s05.t02.n01.entity.Card;
import cat.itacademy.s05.t02.n01.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameDto {
    private String id;
    private PlayerDto player;
    private List<Card> playerHand;
    private int playerPoints;
    private List<Card> crupierHand;
    private int crupierPoints;
    private Game.Status status;
}
