package nl.hu.bep2.casino.blackjack.game.presentation.dto;

import javax.validation.constraints.Positive;

public class BetDTO {
    @Positive
    public long bet;
}
