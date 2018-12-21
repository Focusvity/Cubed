package me.focusvity.cubed.command.information;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
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
    protected void execute(CommandEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args.length > 1)
        {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setDescription("Found too many arguments (" + args.length + ")")
                    .build();
            event.reply(embed);
            return;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Help Information for " + event.getSelfMember().getEffectiveName())
                .setThumbnail(event.getSelfUser().getAvatarUrl())
                .setColor(Color.WHITE);

        for (CCommand command : getCommands())
        {
            builder.addField(command.getName(),
                    "Description: " + command.getHelp() + "\n"
                            + "Usage: " + command.getName() + " " + (command.getArguments() != null ? command.getArguments() : "") + "\n"
                            + (command.getAliases().length > 0 ? "Aliases: " + StringUtils.join(command.getAliases(), ", ") : ""),
                    false);
        }

        event.reply(builder.build());
    }
}
