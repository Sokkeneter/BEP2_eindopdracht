package nl.hu.bep2.casino.blackjack.game.presentation;

import nl.hu.bep2.casino.blackjack.game.application.GameService;
import nl.hu.bep2.casino.blackjack.game.application.exception.GameOverException;
import nl.hu.bep2.casino.blackjack.game.application.exception.NoGameFoundException;
import nl.hu.bep2.casino.blackjack.game.presentation.dto.BetDTO;
import nl.hu.bep2.casino.blackjack.game.presentation.dto.ChoiceDTO;
import nl.hu.bep2.casino.blackjack.game.domain.Game;
import nl.hu.bep2.casino.chips.domain.exception.NegativeNumberException;
import nl.hu.bep2.casino.security.domain.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/blackjack")
public class GameController {
    private final GameService service;

    public GameController(GameService gameService) {
        this.service = gameService;
    }

    @GetMapping
    public Game getGame(Authentication authentication) {
        try{
            UserProfile profile = (UserProfile) authentication.getPrincipal();
            Game game = service.findCurrentGameByUsername(profile.getUsername());
            return game;
        }catch (NoGameFoundException ngfe){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ngfe.getMessage());
        }
    }

    @PostMapping
    public Game startGame(Authentication authentication, @Validated @RequestBody BetDTO betDTO) {
        try{
            UserProfile profile = (UserProfile) authentication.getPrincipal();
            return service.startGame(profile.getUsername(), betDTO.bet);
        }catch (NegativeNumberException exception){
            System.out.println(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PutMapping("/choice")
    public Game chooseOption(Authentication authentication, @RequestBody ChoiceDTO choiceDTO) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        try{
            return service.chooseOption(profile.getUsername(), choiceDTO.choice);
        }catch (GameOverException goe){
            throw new ResponseStatusException(HttpStatus.CONFLICT, goe.getMessage());
        }catch (NoGameFoundException ngfe){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ngfe.getMessage());
        }
    }

    @DeleteMapping("/all")
    public void deleteAll(Authentication authentication){
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        service.deleteAllFromUser(profile.getUsername());
    }
}
