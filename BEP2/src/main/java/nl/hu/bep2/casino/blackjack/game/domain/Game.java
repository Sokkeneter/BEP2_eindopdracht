package nl.hu.bep2.casino.blackjack.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.hu.bep2.casino.blackjack.card.domain.Card;
import nl.hu.bep2.casino.blackjack.hand.domain.Hand;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.security.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    @OneToOne (cascade = CascadeType.ALL)
    private Hand playerHand;
    @OneToOne (cascade = CascadeType.ALL)
    private Hand dealerHand;
    @JsonIgnore
    @OneToMany (cascade = CascadeType.ALL)
    List<Card> deck;
    private long bet;
    @Enumerated
    private State playerState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game(){}

    public Game(String username, Hand playerHand, Hand dealerHand, List<Card> deck, long bet) {
        this.username = username;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.deck = deck;
        this.bet = bet;
        this.playerState = State.ONGOING;
    }

    public Game(String username, List<Card> deck,long bet){
        this.username = username;
        this.bet = bet;
        this.deck = deck;

        this.playerState = State.ONGOING;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public long getBet() {
        return bet;
    }

    public void setBet(long bet) {
        this.bet = bet;
    }

    public State getPlayerState() {
        return playerState;
    }

    public void setPlayerState(State playerState) {
        this.playerState = playerState;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }



    public void hit(ChipsService chipsService){
//        deal a card
        playerHand.dealRandomCard(deck);
        //      turn acecvalue into 1 if necessary
        playerHand.checkAceValue();
        dealerHand.checkAceValue();
//        get total value of hand
        int playerTotal = this.getPlayerHand().getTotal();
        int dealerTotal = this.getDealerHand().getTotal();

//       check for blackjack
        if(playerTotal == 21){
            chipsService.depositChips(this.getUsername(), (bet*2));
            this.setPlayerState(State.BLACKJACK);
        }
        //       check for busts
        if(playerTotal > 21){
            this.setPlayerState(State.BUST);
        }
    }
    public void stand(ChipsService chipsService){
//        deal card to dealerhand if it's lower than 17
        if (this.getDealerHand().getTotal() < 17) {
            dealerHand.dealRandomCard(deck);
        }
        //      turn acecvalue into 1 if necessary
        playerHand.checkAceValue();
        dealerHand.checkAceValue();
//        get total value of hand
        int playerTotal = this.getPlayerHand().getTotal();
        int dealerTotal = this.getDealerHand().getTotal();

        //       check for wins
        if(playerTotal > dealerTotal){
            chipsService.depositChips(this.getUsername(), (long) (bet*(1.5)));
            this.setPlayerState(State.STANDARD_WIN);
        }
        else if(dealerTotal > 21){ //different than blackjack cause this only runs when the dealers cards are shown
            chipsService.depositChips(this.getUsername(), (bet*(2)));
            this.setPlayerState(State.STANDARD_WIN);
        }
        else{// dealer hand and player hand are the same value. other options are covered every time you do a hit
            this.setPlayerState(State.DRAW);
        }
    }

    public Game chooseOption(Action choice, ChipsService chipservice) {
       if (choice.equals(Action.HIT)) {
            hit(chipservice);
        } else if (choice.equals(Action.STAND)) {
           stand(chipservice);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not a choice");
        }
        return this;
    }

    public Game startGame(UserService userService){
        userService.loadUserByUsername(username).getCurrentGame().setPlayerState(State.DRAW);

        this.setPlayerHand(new Hand());
        this.setDealerHand(new Hand());
//        deal the cards you get at the beginning
        playerHand.dealRandomCard(deck);
        playerHand.dealRandomCard(deck);

        dealerHand.dealRandomCard(deck);
        dealerHand.dealRandomCard(deck);
        return this;
    }
}
