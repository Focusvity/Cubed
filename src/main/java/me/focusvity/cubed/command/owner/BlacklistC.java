package me.focusvity.cubed.command.owner;

import me.focusvity.cubed.blacklist.BlacklistManager;
import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class BlacklistC extends CCommand
{

    public BlacklistC()
    {
        this.name = "blacklist";
        this.help = "Blacklist an user";
        this.arguments = "list | {id} {reason}";
        this.category = Category.OWNER;
        this.ownerCommand = true;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        JDA api = event.getJDA();

        if (args.length == 2 && args[1].equalsIgnoreCase("list"))
        {
            reply(BlacklistManager.getBlacklistedUsers());
            return;
        }

        if (args.length < 3)
        {
            reply("Not enough arguments!");
            return;
        }

        String id = args[1];
        String reason = StringUtils.join(args, " ", 2, args.length);

        if (BlacklistManager.isBlacklisted(id))
        {
            reply("That id is already blacklisted!");
            return;
        }

        BlacklistManager.addBlacklist(id, event.getAuthor().getId(), reason);
        reply("Successfully added `"
                + api.getUserById(id).getName() + "#"
                + api.getUserById(id).getDiscriminator()
                + "` (" + id + ")");
    }
}
