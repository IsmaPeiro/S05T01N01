package cat.itacademy.s05.t02.n01.entity;

import cat.itacademy.s05.t02.n01.enums.Rank;
import cat.itacademy.s05.t02.n01.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Deck {
    private List<Card> cards;
    
    public void createDeck() {
        cards = Stream.of(Rank.values()).flatMap(rank ->
                Stream.of(Suit.values()).map(suit ->
                        new Card(rank, suit))).collect(Collectors.toList());
        shuffleDeck();
    }
    
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
    
    public List<Card> dealCards(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("The number of cards to deal cannot be negative.");
        }
        if (num > this.cards.size()) {
            throw new IllegalArgumentException("Not enough cards to deal.");
        }
        return IntStream.range(0, num).mapToObj(card -> cards.removeLast()).collect(Collectors.toList());
    }
}
