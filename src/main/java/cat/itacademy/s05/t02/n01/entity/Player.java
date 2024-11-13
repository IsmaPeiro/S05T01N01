package cat.itacademy.s05.t02.n01.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table (name="players")
public class Player {
    @Id
    private Long id;
    private String nickname;
    private int cardGamesWon;
    private int cardGamesLost;
    private int cardGamesDraw;
    
    public int calculateScore() {
        return (cardGamesWon * 10 + cardGamesDraw * 5) - (cardGamesLost * 10);
    }
}
