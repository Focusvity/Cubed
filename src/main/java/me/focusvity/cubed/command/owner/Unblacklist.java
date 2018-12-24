package me.focusvity.cubed.command.owner;

import me.focusvity.cubed.blacklist.BlacklistManager;
import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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
    public void execute(MessageReceivedEvent event, String[] args)
    {
        JDA api = event.getJDA();

        if (args.length < 2)
        {
            reply("Not enough argument!");
            return;
        }

        String id = args[1];

        if (!BlacklistManager.isBlacklisted(id))
        {
            reply("That id isn't blacklisted!");
            return;
        }

        BlacklistManager.removeBlacklist(args[1]);
        reply("Successfully removed blacklist from `"
                + api.getUserById(id).getName() + "#"
                + api.getUserById(id).getDiscriminator()
                + "` (" + id + ")");
    }
}
