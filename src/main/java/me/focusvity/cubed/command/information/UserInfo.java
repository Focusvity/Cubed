package me.focusvity.cubed.command.information;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.util.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class UserInfo extends CCommand
{

    public UserInfo()
    {
        this.name = "userinfo";
        this.arguments = "{@mention}";
        this.help = "Shows information about yourself or user you mentioned";
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        if (args.length > 2)
        {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.RED)
                    .setDescription("Found too many arguments (" + args.length + ").")
                    .build();
            reply(embed);
            return;
        }

        if (args.length == 2)
        {
            Member member = Utils.findMember(event.getMessage(), event.getTextChannel());

            if (member == null)
            {
                return;
            }

            User user = member.getUser();

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(member.getColor())
                    .setThumbnail(user.getAvatarUrl())
                    .setTitle("Information for " + member.getEffectiveName())
                    .addField("Username", user.getName(), true)
                    .addField("Discriminator", user.getDiscriminator(), true)
                    .addField("ID", user.getId(), true)
                    .addField("Is Bot?", (user.isBot() ? "True" : "False"), true)
                    .addField("Creation", user.getCreationTime().toString(), true)
                    .build();
            reply(embed);
            return;
        }
        else
        {
            Member member = event.getMember();
            User user = member.getUser();

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(member.getColor())
                    .setThumbnail(user.getAvatarUrl())
                    .setTitle("Information for " + member.getEffectiveName())
                    .addField("Username", user.getName(), true)
                    .addField("Discriminator", user.getDiscriminator(), true)
                    .addField("ID", user.getId(), true)
                    .addField("Is Bot?", (user.isBot() ? "True" : "False"), true)
                    .addField("Creation", user.getCreationTime().toString(), true)
                    .build();
            reply(embed);
        }
    }
}
