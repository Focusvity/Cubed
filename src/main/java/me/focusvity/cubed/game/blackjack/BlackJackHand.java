package me.focusvity.cubed.game.blackjack;

import me.focusvity.cubed.game.card.Card;
import me.focusvity.cubed.game.card.CardHand;
import me.focusvity.cubed.game.card.CardRank;

public class BlackJackHand extends CardHand
{

    public int getValue()
    {
        int value = 0;
        int aces = 0;

        for (Card card : getCardsInHand())
        {
            if (card.getRank().equals(CardRank.ACE))
            {
                aces++;
            }

            value += card.getRank().getValue();
        }

        while (aces > 0 && value > 21)
        {
            value -= 10;
            aces--;
        }

        return value;
    }

    public String printHand()
    {
        StringBuilder hand = new StringBuilder();

        for (Card card : getCardsInHand())
        {
            hand.append(card.toEmote()).append(" ");
        }

        return hand.toString();
    }
}
