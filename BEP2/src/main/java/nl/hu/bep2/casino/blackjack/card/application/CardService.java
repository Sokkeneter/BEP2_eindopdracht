package nl.hu.bep2.casino.blackjack.card.application;

import nl.hu.bep2.casino.blackjack.card.data.CardRepository;
import nl.hu.bep2.casino.blackjack.card.domain.Card;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card saveCard(Card card){
        return cardRepository.save(card);
    }

    public static List<Card> getStandartDeck(){
        return Card.getStandartDeck();
    }
}
