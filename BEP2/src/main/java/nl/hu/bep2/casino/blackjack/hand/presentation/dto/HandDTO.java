package nl.hu.bep2.casino.blackjack.hand.presentation.dto;

import nl.hu.bep2.casino.blackjack.card.domain.Card;

import java.util.List;

public class HandDTO {
    public int handId;
    public List<Card> cards;
    public String username;
}
