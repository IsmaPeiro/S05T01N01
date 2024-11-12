package cat.itacademy.s05.t02.n01.entity;

import lombok.*;

@Data
public class Card {
    
    @RequiredArgsConstructor
    @Getter
    public enum Rank {
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);
        private final int value;
    }
    
    public enum Suit {
        DIAMONDS, CLUBS, HEARTS, SPADES
    }
    
    private final Rank rank;
    private final Suit suit;
}
