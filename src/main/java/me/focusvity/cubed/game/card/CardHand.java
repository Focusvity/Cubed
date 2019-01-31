package me.focusvity.cubed.game.card;

import lombok.Getter;

import java.util.ArrayList;

public class CardHand
{

    @Getter
    private ArrayList<Card> cardsInHand;

    public CardHand()
    {
        reset();
    }

    public void reset()
    {
        cardsInHand = new ArrayList<>();
    }

    public void add(Card card)
    {
        cardsInHand.add(card);
    }

    public boolean remove(Card card)
    {
        return cardsInHand.remove(card);
    }
}
