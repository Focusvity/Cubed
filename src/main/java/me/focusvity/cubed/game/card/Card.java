package me.focusvity.cubed.game.card;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Card
{

    private static final List<Card> deck = new ArrayList<>();

    static
    {
        for (CardSuit suit : CardSuit.values())
        {
            for (CardRank rank : CardRank.values())
            {
                deck.add(new Card(rank, suit));
            }
        }
    }

    @Getter
    private final CardRank rank;
    @Getter
    private final CardSuit suit;

    public Card(CardRank rank, CardSuit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public static ArrayList<Card> newDeck()
    {
        return new ArrayList<>(deck);
    }

    @Override
    public String toString()
    {
        return rank.getName() + " of " + suit.getName();
    }

    public String toEmote()
    {
        int value = rank.getValue();
        String emote = String.valueOf(value);

        if ((value == 10 && !rank.equals(CardRank.TEN)) || value == 11)
        {
            emote = rank.getName().substring(0, 1).toUpperCase();
        }

        return "[" + suit.getEmote() + emote + "]";
    }
}
