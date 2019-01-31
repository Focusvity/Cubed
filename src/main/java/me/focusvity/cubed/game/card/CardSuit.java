package me.focusvity.cubed.game.card;

import lombok.Getter;

public enum CardSuit
{

    CLUBS(":clubs:"),
    DIAMONDS(":diamonds:"),
    HEARTS(":hearts:"),
    SPADES(":spades:");

    @Getter
    private final String name;
    @Getter
    private final String emote;

    CardSuit(String emote)
    {
        this.name = name().toLowerCase();
        this.emote = emote;
    }
}
