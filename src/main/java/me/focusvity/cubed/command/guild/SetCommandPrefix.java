package me.focusvity.cubed.command.guild;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.SQLManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetCommandPrefix extends CCommand
{

    public SetCommandPrefix()
    {
        this.name = "setcommandprefix";
        this.help = "Set a command prefix for your server";
        this.arguments = "{prefix}";
        this.aliases = new String[]{"setcmdprefix"};
        this.category = Category.GUILD;
        this.permissions = new Permission[]
                {
                        Permission.MESSAGE_MANAGE,
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

        String prefix = args[1];
        SQLManager.updateTable("guilds", "id", event.getGuild().getId(), "cmdprefix", prefix);
        reply("The guild's command prefix has been set to `" + prefix + "`");
    }
}
