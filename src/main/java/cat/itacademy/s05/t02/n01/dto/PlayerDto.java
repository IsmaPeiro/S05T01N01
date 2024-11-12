package cat.itacademy.s05.t02.n01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerDto {
    private Long id;
    private String nickname;
    private int score;
    private int cardGamesWon;
    private int cardGamesLost;
    private int cardGamesDraw;
}
