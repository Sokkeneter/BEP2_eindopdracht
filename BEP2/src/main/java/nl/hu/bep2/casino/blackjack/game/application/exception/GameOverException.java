package nl.hu.bep2.casino.blackjack.game.application.exception;

public class GameOverException extends RuntimeException {
    public GameOverException() {
        super("this game has already finished");
    }
}
