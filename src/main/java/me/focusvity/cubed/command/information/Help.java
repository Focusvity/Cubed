package me.focusvity.cubed.command.information;

import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public class Help extends CCommand
{

    public Help()
    {
        this.name = "help";
        this.help = "Shows this helpful information";
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        if (args.length > 1)
        {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setDescription("Found too many arguments (" + args.length + ")")
                    .build();
            reply(embed);
            return;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Help Information for " + event.getMember().getEffectiveName())
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setColor(Color.WHITE);

        for (CCommand command : getCommands())
        {
            builder.addField(command.getName(),
                    "Description: " + command.getHelp() + "\n"
                            + "Usage: " + command.getName() + " " + (command.getArguments() != null ? command.getArguments() : "") + "\n"
                            + (command.getAliases().length > 0 ? "Aliases: " + StringUtils.join(command.getAliases(), ", ") : ""),
                    false);
        }

        reply(builder.build());
    }
}
