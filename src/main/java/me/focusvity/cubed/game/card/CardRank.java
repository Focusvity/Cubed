package me.focusvity.cubed.game.card;

import lombok.Getter;

public enum CardRank
{

    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9),
    TEN("ten", 10),
    JACK("jack", 10),
    QUEEN("queen", 10),
    KING("king", 10),
    ACE("ace", 11);

    @Getter
    private final String name;
    @Getter
    private final int value;

    CardRank(String name, int value)
    {
        this.name = name;
        this.value = value;
    }
}
