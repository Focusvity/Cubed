package me.focusvity.cubed.command.fun;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.util.Quotes;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.util.Random;

public class Quote extends CCommand
{

    public Quote()
    {
        this.name = "quote";
        this.help = "Quotes from random people";
        this.arguments = "{name}";
    }

    @Override
    public void execute(CommandEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args.length != 2)
        {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setDescription("Found not enough or too many arguments (Requires 2)")
                    .build();
            event.reply(embed);
            return;
        }

        Quotes quote = Quotes.findQuote(event.getTextChannel(), args[1]);
        if (quote == null)
        {
            return;
        }

        Random random = new Random();
        int use = random.nextInt(quote.getQuotes().length);
        event.reply(quote.getQuotes()[use]);
    }
}
