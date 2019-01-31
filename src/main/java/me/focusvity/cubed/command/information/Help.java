package me.focusvity.cubed.command.information;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Help extends CCommand
{

    public Help()
    {
        this.name = "help";
        this.help = "Shows this helpful information";
        this.category = Category.INFORMATION;
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

        List<String> fun = new ArrayList<>();
        List<String> guild = new ArrayList<>();
        List<String> information = new ArrayList<>();
        List<String> moderation = new ArrayList<>();
        List<String> music = new ArrayList<>();
        List<String> owner = new ArrayList<>();

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Help Information for " + event.getJDA().getSelfUser().getName())
                .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                .setColor(Color.WHITE);

        for (CCommand command : getCommands())
        {
            switch (command.getCategory())
            {
                case FUN:
                {
                    fun.add("`" + command.getName() + "`");
                    break;
                }
                case GUILD:
                {
                    guild.add("`" + command.getName() + "`");
                    break;
                }
                case INFORMATION:
                {
                    information.add("`" + command.getName() + "`");
                    break;
                }
                case MODERATION:
                {
                    moderation.add("`" + command.getName() + "`");
                    break;
                }
                case MUSIC:
                {
                    music.add("`" + command.getName() + "`");
                    break;
                }
                case OWNER:
                {
                    owner.add("`" + command.getName() + "`");
                    break;
                }
            }
        }

        builder.addField("Fun", StringUtils.join(fun, ", "), false);
        builder.addField("Guild", StringUtils.join(guild, ", "), false);
        builder.addField("Information", StringUtils.join(information, ", "), false);
        builder.addField("Moderation", StringUtils.join(moderation, ", "), false);
        builder.addField("Music", StringUtils.join(music, ", "), false);
        builder.addField("Owner", StringUtils.join(owner, ", "), false);

        reply(builder.build());
    }
}
