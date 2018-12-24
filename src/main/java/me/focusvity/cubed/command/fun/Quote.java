package me.focusvity.cubed.command.fun;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.util.Quotes;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
    public void execute(MessageReceivedEvent event, String[] args)
    {
        if (args.length != 2)
        {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setDescription("Found not enough or too many arguments (Requires 2)")
                    .build();
            reply(embed);
            return;
        }

        Quotes quote = Quotes.findQuote(args[1]);
        if (quote == null)
        {
            reply("I couldn't find any quote for `" + args[1] + "`");
            return;
        }

        Random random = new Random();
        int use = random.nextInt(quote.getQuotes().length);
        reply(quote.getQuotes()[use]);
    }
}
