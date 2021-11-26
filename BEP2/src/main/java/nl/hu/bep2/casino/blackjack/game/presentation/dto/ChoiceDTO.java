package nl.hu.bep2.casino.blackjack.game.presentation.dto;

import nl.hu.bep2.casino.blackjack.game.domain.Action;

import javax.persistence.Enumerated;

public class ChoiceDTO {
    @Enumerated
    public Action choice;
}
