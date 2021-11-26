package nl.hu.bep2.casino.blackjack.hand.application;


import nl.hu.bep2.casino.blackjack.game.application.GameService;
import nl.hu.bep2.casino.blackjack.game.domain.Game;
import nl.hu.bep2.casino.blackjack.hand.data.HandRepository;
import nl.hu.bep2.casino.blackjack.hand.domain.Hand;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public final class HandService {
    private final GameService gameService;


    public HandService(GameService gameService) {
        this.gameService = gameService;
    }

    public Hand dealRandomCard(String username, Hand hand){
        Game game = gameService.findCurrentGameByUsername(username);
        return hand.dealRandomCard(game.getDeck());
    }

}
