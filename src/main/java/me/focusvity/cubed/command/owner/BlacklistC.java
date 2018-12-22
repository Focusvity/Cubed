package me.focusvity.cubed.command.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.blacklist.BlacklistManager;
import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.JDA;
import org.apache.commons.lang3.StringUtils;

public class BlacklistC extends CCommand
{

    public BlacklistC()
    {
        this.name = "blacklist";
        this.help = "Blacklist an user";
        this.arguments = "{id} {reason}";
        this.ownerCommand = true;
    }

    @Override
    public void execute(CommandEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");
        JDA api = event.getJDA();

        if (args.length < 3)
        {
            event.reply("Not enough arguments!");
            return;
        }

        String id = args[1];
        String reason = StringUtils.join(args, " ", 2, args.length);

        if (BlacklistManager.isBlacklisted(id))
        {
            event.reply("That id is already blacklisted!");
            return;
        }

        BlacklistManager.addBlacklist(id, event.getAuthor().getId(), reason);
        event.reply("Successfully added `"
                + api.getUserById(id).getName() + "#"
                + api.getUserById(id).getDiscriminator()
                + "` (" + id + ")");
    }
}
