package nl.hu.bep2.casino.blackjack.hand.data;

import nl.hu.bep2.casino.blackjack.game.domain.Game;
import nl.hu.bep2.casino.blackjack.hand.domain.Hand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HandRepository extends JpaRepository<Hand, String> {
}


