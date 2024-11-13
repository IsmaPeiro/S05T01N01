package cat.itacademy.s05.t02.n01.entity;

import cat.itacademy.s05.t02.n01.dto.PlayerDto;
import cat.itacademy.s05.t02.n01.enums.Action;
import cat.itacademy.s05.t02.n01.enums.Rank;
import cat.itacademy.s05.t02.n01.enums.Status;
import cat.itacademy.s05.t02.n01.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "game")
public class Game {
    @Id
    private String id;
    private PlayerDto player;
    private Deck deck;
    private List<Card> playerHand;
    private int playerPoints;
    private List<Card> crupierHand;
    private int crupierPoints;
    private Status status;
    
    public Game(PlayerDto player) {
        this.player = player;
        deck = new Deck();
        playerHand = new ArrayList<>();
        crupierHand = new ArrayList<>();
        status = Status.ON_PROGRESS;
    }
    
    public void startGame() {
        deck.createDeck();
        playerHand = deck.dealCards(2);
        crupierHand = deck.dealCards(2);
        playerPoints = calculatePoints(playerHand);
        crupierPoints = calculatePoints(crupierHand);
        checkFirstHand();
    }
    
    private void checkFirstHand() {
        if (playerPoints == 21 && crupierPoints == 21) {
            status = Status.DRAW;
            player.setCardGamesDraw(player.getCardGamesDraw() + 1);
        } else if (playerPoints == 21) {
            status = Status.PLAYER_WINS;
            player.setCardGamesWon(player.getCardGamesWon() + 1);
        } else if (crupierPoints == 21) {
            status = Status.CRUPIER_WINS;
            player.setCardGamesLost(player.getCardGamesLost() + 1);
        }
    }
    
    public int calculatePoints(List<Card> hand) {
        int points = hand.stream().mapToInt(card -> card.rank().getValue()).sum();
        int aceCounter = (int) hand.stream().filter(card -> card.rank() == Rank.ACE).count();
        while (points > 21 && aceCounter > 0) {
            points -= 10;
            aceCounter--;
        }
        return points;
    }
    
    public void makeAMove(Action action) {
        switch (action) {
            case HIT -> dealCard();
            case STAND -> crupierMove();
            default -> throw new IllegalArgumentException("Action no valid");
        }
    }
    
    private void dealCard() {
        if (status != Status.ON_PROGRESS) {
            throw new IllegalStateException("The game is over.");
        }
        playerHand.addAll(deck.dealCards(1));
        playerPoints = calculatePoints(playerHand);
        if (playerPoints > 21) {
            status = Status.CRUPIER_WINS;
            player.setCardGamesLost(player.getCardGamesLost() + 1);
        }
    }
    
    private void crupierMove() {
        while (crupierPoints < 17) {
            crupierHand.addAll(deck.dealCards(1));
            crupierPoints = calculatePoints(crupierHand);
        }
        if (crupierPoints > 21) {
            status = Status.PLAYER_WINS;
            player.setCardGamesWon(player.getCardGamesWon() + 1);
        } else {
            updateGame();
        }
    }
    
    private void updateGame() {
        if (crupierPoints > playerPoints) {
            status = Status.CRUPIER_WINS;
            player.setCardGamesLost(player.getCardGamesLost() + 1);
        } else if (crupierPoints == playerPoints) {
            status = Status.DRAW;
            player.setCardGamesDraw(player.getCardGamesDraw() + 1);
        } else {
            status = Status.PLAYER_WINS;
            player.setCardGamesWon(player.getCardGamesWon() + 1);
        }
    }
}

