package me.focusvity.cubed.command.guild;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.SQLManager;
import me.focusvity.cubed.util.Utils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetAutoRole extends CCommand
{

    public SetAutoRole()
    {
        this.name = "setautorole";
        this.help = "Set a role that will automatically added to new members.";
        this.arguments = "{role}";
        this.category = Category.GUILD;
        this.permissions = new Permission[]
                {
                        Permission.MANAGE_ROLES,
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

        Role role = Utils.findRole(event.getMessage(), event.getTextChannel());

        if (role != null)
        {
            SQLManager.updateTable("guilds", "id", event.getGuild().getId(), "autorole", role.getId());
            reply("The guild's auto role has been set to " + role.getName());
        }
        else
        {
            role = event.getJDA().getRoleById(args[1]);
            if (role == null)
            {
                reply("Couldn't find any roles under that id!");
                return;
            }

            SQLManager.updateTable("guilds", "id", event.getGuild().getId(), "autorole", role.getId());
            reply("The guild's auto role has been set to " + role.getName());
        }
    }
}
