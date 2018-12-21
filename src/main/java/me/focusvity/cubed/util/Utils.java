package me.focusvity.cubed.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class Utils
{

    public static Member findMember(Member first, String query, CommandEvent event)
    {
        List<Member> members = FinderUtil.findMembers(query, event.getGuild());

        if (members.isEmpty() && !query.isEmpty())
        {
            event.reply(":warning: I couldn't find anyone with that name!");
            return null;
        }

        if (members.size() > 1 && !query.isEmpty())
        {
            event.reply(":warning: I have found more than one user with that name! Please refine your search.");
            return null;
        }

        if (members.size() == 1)
        {
            return members.get(0);
        }

        return first;
    }

    public static Member findMember(Message message, TextChannel channel)
    {
        List<Member> members = message.getMentionedMembers();

        if (members.isEmpty())
        {
            channel.sendMessage(":x: I couldn't find anyone! Did you mention them?").queue();
            return null;
        }

        if (members.size() > 1)
        {
            channel.sendMessage(":warning: Sorry, too many people!");
            return null;
        }

        return members.get(0);
    }
}
