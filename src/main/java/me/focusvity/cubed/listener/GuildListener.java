package me.focusvity.cubed.listener;

import me.focusvity.cubed.util.ModAction;
import me.focusvity.cubed.util.SQLManager;
import me.focusvity.cubed.util.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuildListener extends ListenerAdapter
{

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        if (!SQLManager.guildExists(event.getGuild()))
        {
            SQLManager.generateNewGuild(event.getGuild());
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        if (SQLManager.guildExists(event.getGuild()))
        {
            SQLManager.deleteGuild(event.getGuild());
        }
    }

    @Override
    public void onReady(ReadyEvent event)
    {
        for (Guild guild : event.getJDA().getGuilds())
        {
            if (!SQLManager.guildExists(guild))
            {
                SQLManager.generateNewGuild(guild);
            }
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        Guild guild = event.getGuild();
        User user = event.getUser();
        Member member = event.getMember();

        TextChannel joinChannel = guild.getTextChannelById(SQLManager.getFromGuilds(guild.getId(), "joinchannel"));
        if (joinChannel != null)
        {
            EmbedBuilder embed = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("New Member!", event.getUser().getEffectiveAvatarUrl());
            embed.addField("Member", user.getName() + "#" + user.getDiscriminator(), false);
            embed.addField("Created At", user.getCreationTime().toString(), false);
            embed.setFooter(":arrow_up_small: Member Count: " + guild.getMembers().size(), null);
            joinChannel.sendMessage(embed.build()).queue();
        }

        Role role = guild.getRoleById(SQLManager.getFromGuilds(guild.getId(), "autorole"));
        if (role != null)
        {
            GuildController controller = new GuildController(guild);
            controller.addRolesToMember(member, role).queue();
        }

        VoiceChannel channel = guild.getVoiceChannelById(SQLManager.getFromGuilds(guild.getId(), "membercount"));
        if (channel != null)
        {
            channel.getManager().setName("User Count: " + guild.getMembers().size()).queue();
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event)
    {
        VoiceChannel channel = event.getGuild().getVoiceChannelById(SQLManager.getFromGuilds(event.getGuild().getId(),
                "membercount"));
        if (channel != null)
        {
            channel.getManager().setName("User Count: " + event.getGuild().getMembers().size()).queue();
        }
    }

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event)
    {
        List<String> roles = new ArrayList<>();

        for (Role role : event.getRoles())
        {
            roles.add(role.getName());
        }

        Utils.modLog(event.getGuild(), ModAction.MEMBER_ROLE_ADD,
                new MessageEmbed.Field("Member", event.getMember().getEffectiveName(), false),
                new MessageEmbed.Field("Role(s)", StringUtils.join(roles, ", "), false));
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event)
    {
        List<String> roles = new ArrayList<>();

        for (Role role : event.getRoles())
        {
            roles.add(role.getName());
        }

        Utils.modLog(event.getGuild(), ModAction.MEMBER_ROLE_REMOVE,
                new MessageEmbed.Field("Member", event.getMember().getEffectiveName(), false),
                new MessageEmbed.Field("Role(s)", StringUtils.join(roles, ", "), false));
    }
}
