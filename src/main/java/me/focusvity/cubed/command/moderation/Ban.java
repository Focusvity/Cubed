package me.focusvity.cubed.command.moderation;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.Utils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class Ban extends CCommand
{

    public Ban()
    {
        this.name = "ban";
        this.help = "Ban a naughty user and delete 7 days of messages from that user.";
        this.arguments = "{user} {reason}";
        this.category = Category.MODERATION;
        this.permissions = new Permission[]
                {
                        Permission.BAN_MEMBERS
                };
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        if (args.length >= 3)
        {
            reply("Requires 3 or more arguments!");
            return;
        }

        Member member = Utils.findMember(event.getMessage(), event.getTextChannel());
        String reason = StringUtils.join(args, " ", 2, args.length);

        if (member != null)
        {
            controller.ban(member, 7, reason).queue();
            reply("Successfully banned " + member.getEffectiveName() + " for " + reason);
        }
        else
        {
            User user = event.getJDA().getUserById(args[1]);
            if (user == null)
            {
                reply("Couldn't find any users!");
                return;
            }

            controller.ban(user, 7, reason).queue();
            reply("Successfully banned " + user.getName() + "#" + user.getDiscriminator() + " for " + reason);
        }
    }
}
