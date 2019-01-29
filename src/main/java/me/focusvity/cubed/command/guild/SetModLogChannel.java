package me.focusvity.cubed.command.guild;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.SQLManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetModLogChannel extends CCommand
{

    public SetModLogChannel()
    {
        this.name = "setmodlogchannel";
        this.help = "Set a channel that will automatically send whenever a moderation action has executed.";
        this.arguments = "{channel id}";
        this.aliases = new String[]
                {
                        "setmlchannel",
                        "setmodlog"
                };
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
            SQLManager.updateTable("guilds", "id", event.getGuild().getId(), "modlog", channel.getId());
            reply("The guild's mod log channel has been set to " + channel.getName());
        }
        else
        {
            reply("Couldn't find any channel under that id!");
        }
    }
}
