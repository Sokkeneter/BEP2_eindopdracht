package nl.hu.bep2.casino.blackjack.hand.domain;

import nl.hu.bep2.casino.blackjack.card.domain.Card;
import nl.hu.bep2.casino.blackjack.card.domain.CardValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Hand {
    @Id
    @GeneratedValue
    private int handId;
    @OneToMany
    private List<Card> cards;
    int total = 0;



    public Hand() {
        cards = new ArrayList<>();
    }

    public Hand dealRandomCard(List<Card> deck){
        Random rand = new Random();
        Card randomCard = deck.get(rand.nextInt(deck.size()));
        return this.addCard(randomCard);
    }

    public Hand addCard(Card card){
        cards.add(card);
        total += card.getCardValue().getValue();
        return this;
    }

    public int getTotal() {
        return total;
    }

    public void checkAceValue(){
        for(Card card : this.cards){
            if(card.getCardValue().equals(CardValue.ACE)){
                if(this.total > 21){
                    total -= 10; //11 - 10 = 1 so the ace is 1 one now
                    //don't ever really need to switch it back since hand doesn't lose cards
                }
            }
        }
    }
}
