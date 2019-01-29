package me.focusvity.cubed.command.guild;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.SQLManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetJoinChannel extends CCommand
{

    public SetJoinChannel()
    {
        this.name = "setjoinchannel";
        this.help = "Set a channel that will automatically send when a new member joins.";
        this.arguments = "{channel id}";
        this.category = Category.GUILD;
        this.permissions = new Permission[]
                {
                        Permission.MANAGE_CHANNEL,
                        Permission.MANAGE_SERVER,
                        Permission.ADMINISTRATOR
                };
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        if (args.length != 2)
        {
            reply("Requires 1 argument!");
            return;
        }

        TextChannel channel = event.getGuild().getTextChannelById(args[1]);

        if (channel != null)
        {
            SQLManager.updateTable("guilds", "id", event.getGuild().getId(), "joinchannel", channel.getId());
            reply("The guild's join channel has been set to " + channel.getName());
        }
        else
        {
            reply("Couldn't find any channel under that id!");
        }
    }
}
