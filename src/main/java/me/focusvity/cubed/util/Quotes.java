package me.focusvity.cubed.util;

import lombok.Getter;

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
                    + " terrisiom",
            ":^)",
            "ah yes",
            "square emo european",
            "wholesome ops",
            ":okretard:"
    }),
    FLEEK(new String[]{
            "irix = isis confirmed",
            "FBI!\nGET IN THE GROUND!!!",
            "You break it, you fix it"
    }),
    NEO(new String[]{
            "you must've dated a handicap",
            "Idk why, but when i see Zekurt, my meat go up o w o",
            "i cant think of anything else right now but i think i beheaded a jew once",
            "i was so drunk that when i saw jake's \"gottem\" picture, i nutted fast"
    });

    @Getter
    private final String[] quotes;

    Quotes(String[] quotes)
    {
        this.quotes = quotes;
    }

    public static Quotes findQuote(String string)
    {
        try
        {
            return valueOf(string.toUpperCase());
        }
        catch (Exception ex)
        {
        }
        return null;
    }
}
