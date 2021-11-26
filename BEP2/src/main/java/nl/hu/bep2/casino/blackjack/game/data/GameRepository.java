package nl.hu.bep2.casino.blackjack.game.data;

import nl.hu.bep2.casino.blackjack.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface GameRepository extends JpaRepository<Game, String> {
    List<Game> findByUsername(String username);
    void deleteByUsername(String username);
}


