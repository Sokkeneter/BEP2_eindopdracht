package nl.hu.bep2.casino.blackjack.card.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

//https://www.coderscampus.com/enums/
@Entity
public class Card {
    @Id
    @GeneratedValue
    private int cardId;
    @Enumerated
    private CardSuit cardSuit;
    @Enumerated
    private CardValue cardValue;

    public Card (CardValue cardValue, CardSuit cardSuit)
    {
        this.cardValue = cardValue;
        this.cardSuit = cardSuit;
    }

    public Card() {

    }


    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public void setCardValue(CardValue cardValue) {
        this.cardValue = cardValue;
    }


    public static List<Card> getStandartDeck(){
        ArrayList<Card> cards = new ArrayList<Card>();
        for(CardSuit suit : CardSuit.values()){
            for (CardValue value : CardValue.values()) {
                Card card = new Card(value, suit);
                cards.add(card);
            }
        }
        return cards;
    }


    @Override
    public String toString() {
        return String.format("Card: suit: %s, type: %s, value: %s", this.cardSuit, this.cardValue, this.cardValue.getValue());
    }
}
