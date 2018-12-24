package me.focusvity.cubed.util;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class Utils
{

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
