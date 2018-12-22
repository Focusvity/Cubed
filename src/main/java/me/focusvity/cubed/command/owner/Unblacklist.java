package me.focusvity.cubed.command.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.blacklist.BlacklistManager;
import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.JDA;

public class Unblacklist extends CCommand
{

    public Unblacklist()
    {
        this.name = "unblacklist";
        this.arguments = "{id}";
        this.help = "Lift blacklist from an user";
        this.ownerCommand = true;
    }

    @Override
    public void execute(CommandEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");
        JDA api = event.getJDA();

        if (args.length < 2)
        {
            event.reply("Not enough argument!");
            return;
        }

        String id = args[1];

        if (!BlacklistManager.isBlacklisted(id))
        {
            event.reply("That id isn't blacklisted!");
            return;
        }

        BlacklistManager.removeBlacklist(args[1]);
        event.reply("Successfully removed blacklist from `"
                + api.getUserById(id).getName() + "#"
                + api.getUserById(id).getDiscriminator()
                + "` (" + id + ")");
    }
}
