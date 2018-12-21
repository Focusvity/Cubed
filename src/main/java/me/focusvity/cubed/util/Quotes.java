package me.focusvity.cubed.util;

import lombok.Getter;
import net.dv8tion.jda.core.entities.TextChannel;

public enum Quotes
{

    SUPER(new String[]{
            "banime",
            "gay",
            "haha yes",
            "speediv mexico",
            "speed = small peepee",
            "lion company",
            "premium mints ex",
            "fortnite gamer",
            "gamer boys",
            "gamer girls",
            "spood 4",
            "remix",
            "lightus",
            "remove furry",
            "hi jake",
            "arsex",
            "thank",
            "fortnite is for gays",
            "lion co",
            "comer la taco",
            "robin mexico",
            "are we the biggest threat to you lately?",
            "default dance supreme",
            "va is south not north you gay",
            "t gay",
            "m"
    }),
    SPEED(new String[]{
            "no anime",
            "brooklyn supreme",
            "hi seth",
            "Rhyfomos",
            "remove robin",
            "i love being a mom but it's tough when my kids come from filthy from radical"
            + "terrisiom",
            ":^)",
            "ah yes",
            "square emo european",
            "wholesome ops",
            ":okretard:"
    }),
    FLEEK(new String[]{
            "irix = isis confirmed"
    });

    @Getter
    private final String[] quotes;

    Quotes(String[] quotes)
    {
        this.quotes = quotes;
    }

    public static Quotes findQuote(TextChannel channel, String string)
    {
        try
        {
            return valueOf(string.toUpperCase());
        }
        catch (Exception ex)
        {
            channel.sendMessage("Can't find a single quote for `" + string + "`");
        }

        channel.sendMessage("I couldn't find any quote for `" + string + "`");
        return null;
    }
}
