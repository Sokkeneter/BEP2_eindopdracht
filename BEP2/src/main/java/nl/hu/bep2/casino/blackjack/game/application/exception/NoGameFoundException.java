package nl.hu.bep2.casino.blackjack.game.application.exception;

public class NoGameFoundException extends RuntimeException {
    public NoGameFoundException() {
        super("no game found for username");
    }
}
