package me.focusvity.cubed.command.moderation;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import me.focusvity.cubed.util.Utils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AddRole extends CCommand
{

    public AddRole()
    {
        this.name = "addrole";
        this.help = "Add a role to an user.";
        this.arguments = "{user} {role}";
        this.category = Category.MODERATION;
        this.permissions = new Permission[]
                {
                        Permission.MANAGE_ROLES
                };
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        if (args.length != 3)
        {
            reply("Requires 2 arguments!");
            return;
        }

        Member member = Utils.findMember(event.getMessage(), event.getTextChannel());
        Role role = Utils.findRole(event.getMessage(), event.getTextChannel());

        if (member == null)
        {
            reply("Couldn't find any members!");
            return;
        }

        if (role != null)
        {
            controller.addRolesToMember(member, role).queue();
            reply("Successfully added " + role.getName() + " to " + member.getEffectiveName());
        }
        else
        {
            role = event.getJDA().getRoleById(args[2]);
            if (role == null)
            {
                reply("Couldn't find any roles!");
                return;
            }

            controller.addRolesToMember(member, role).queue();
            reply("Successfully added " + role.getName() + " to " + member.getEffectiveName());
        }
    }
}
