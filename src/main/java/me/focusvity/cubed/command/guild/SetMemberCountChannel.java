package me.focusvity.cubed.command.guild;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.SQLManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetMemberCountChannel extends CCommand
{

    public SetMemberCountChannel()
    {
        this.name = "setmembercountchannel";
        this.help = "Set a channel that will automatically update when a member joins or leaves.";
        this.arguments = "{channel id}";
        this.aliases = new String[]
                {
                        "setmcchannel",
                        "setmembercount"
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

        VoiceChannel channel = event.getGuild().getVoiceChannelById(args[1]);

        if (channel != null)
        {
            SQLManager.updateTable("guilds", "id", event.getGuild().getId(), "membercount", channel.getId());
            reply("The guild's join channel has been set to " + channel.getName());
        }
        else
        {
            reply("Couldn't find any channel under that id!");
        }
    }
}
