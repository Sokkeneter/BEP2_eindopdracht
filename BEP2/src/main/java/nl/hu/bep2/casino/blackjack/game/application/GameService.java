package nl.hu.bep2.casino.blackjack.game.application;


import nl.hu.bep2.casino.blackjack.card.application.CardService;
import nl.hu.bep2.casino.blackjack.game.application.exception.GameOverException;
import nl.hu.bep2.casino.blackjack.game.application.exception.NoGameFoundException;
import nl.hu.bep2.casino.blackjack.game.data.GameRepository;
import nl.hu.bep2.casino.blackjack.game.domain.Action;
import nl.hu.bep2.casino.blackjack.game.domain.Game;
import nl.hu.bep2.casino.blackjack.game.domain.State;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.security.application.UserService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public final class GameService {
    private final UserService userService;
    private final ChipsService chipsService;
    private final GameRepository gameRepository;


    public GameService(UserService userService, ChipsService chipsService, GameRepository gameRepository) {
        this.userService = userService;
        this.chipsService = chipsService;
        this.gameRepository = gameRepository;
    }

    private Game saveGame(Game game){
        return gameRepository.save(game);
    }



    public Game startGame(String username, long bet ){

        Game game = new Game(username, CardService.getStandartDeck(), bet);
        game = game.startGame(userService);
        userService.loadUserByUsername(username).setCurrentGame(game);
        chipsService.withdrawChips(username, bet);
        return gameRepository.save(game);
    }


    public Game chooseOption(String username, Action choice) {
        Game game = userService.loadUserByUsername(username).getCurrentGame();
        if(game.getPlayerState().equals(State.ONGOING)) {
            Game gameTobBeSaved = game.chooseOption(choice, chipsService);
            return saveGame(gameTobBeSaved);
        }
        else throw new GameOverException();
    }


    public Game findCurrentGameByUsername(String username) throws NoGameFoundException {
        return userService.loadUserByUsername(username).getCurrentGame();
    }


    public void deleteAllFromUser(String username){
        userService.loadUserByUsername(username).removeCurrentGame();
        gameRepository.deleteByUsername(username);
    }
}
