package me.focusvity.cubed.game.blackjack;

import lombok.Getter;
import me.focusvity.cubed.game.card.Card;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.Collections;

public class BlackJack
{

    private final User user;
    private BlackJackHand playerHand;
    private BlackJackHand dealerHand;
    private ArrayList<Card> deck;
    @Getter
    private boolean inProgress = true;
    @Getter
    private boolean playerStanding = false;

    public BlackJack(User user)
    {
        this.user = user;
        resetGame();
    }

    public String printPlayerHand()
    {
        return playerHand.printHand();
    }

    public String printDealerHand()
    {
        return dealerHand.printHand();
    }

    public int getPlayerValue()
    {
        return playerHand.getValue();
    }

    public int getDealerValue()
    {
        return dealerHand.getValue();
    }

    private Card drawCard()
    {
        return deck.remove(0);
    }

    public void hit()
    {
        if (playerStanding)
        {
            return;
        }

        if (getPlayerValue() == 0)
        {
            playerHand.add(drawCard());
        }

        playerHand.add(drawCard());

        if (getDealerValue() == 0)
        {
            dealerHand.add(drawCard());
        }

        if (getPlayerValue() > 21)
        {
            inProgress = false;
        }
    }

    public boolean dealerHit()
    {
        if (getPlayerValue() <= 21 && getDealerValue() < 21 && getDealerValue() <= getPlayerValue())
        {
            dealerHand.add(drawCard());
            return true;
        }

        inProgress = false;
        return false;
    }

    public void stand()
    {
        playerStanding = true;
    }

    public void resetGame()
    {
        playerHand = new BlackJackHand();
        dealerHand = new BlackJackHand();
        deck = Card.newDeck();
        Collections.shuffle(deck);
        inProgress = true;
        playerStanding = false;
    }

    @Override
    public String toString()
    {
        StringBuilder info = new StringBuilder("Blackjack game:\n");
        info.append(String.format("Dealer's hand (%s):\n", getDealerValue()));
        info.append(printDealerHand()).append("\n\n");
        info.append(String.format("%s's hand (%s):\n", user.getAsMention(), getPlayerValue()));
        info.append(printPlayerHand()).append("\n");

        if (getPlayerValue() > 21)
        {
            info.append("***BUST!*** I win this round.");
        }
        else if (!inProgress)
        {
            info.append("\n");

            if (getPlayerValue() == getDealerValue())
            {
                info.append("***DRAW!*** Congratulations, it's a tie.");
            }
            else if (getPlayerValue() > getDealerValue() || getDealerValue() > 21)
            {
                info.append("***YOU WIN!*** Congratulations, you win this round.");
            }
            else
            {
                info.append("***YOU LOST!*** Sorry, I win this round.");
            }
        }

        return info.toString();
    }
}
