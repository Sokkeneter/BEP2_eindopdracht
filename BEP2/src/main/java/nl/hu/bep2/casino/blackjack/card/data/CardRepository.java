package nl.hu.bep2.casino.blackjack.card.data;

import nl.hu.bep2.casino.blackjack.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}


