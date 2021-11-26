package nl.hu.bep2.casino.blackjack.card.domain;


public enum CardValue {
    ACE(11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int Value;

    CardValue(int value)
    {
        this.Value = value;
    }

    public int getValue() {
        return Value;
    }



}
