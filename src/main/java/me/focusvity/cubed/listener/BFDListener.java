package me.focusvity.cubed.listener;

import me.focusvity.cubed.util.Utils;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.UnavailableGuildJoinedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BFDListener extends ListenerAdapter
{

    @Override
    public void onReady(ReadyEvent event)
    {
        Utils.updateBotsForDiscordCount(event.getJDA());
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        Utils.updateBotsForDiscordCount(event.getJDA());
    }

    @Override
    public void onUnavailableGuildJoined(UnavailableGuildJoinedEvent event)
    {
        Utils.updateBotsForDiscordCount(event.getJDA());
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        Utils.updateBotsForDiscordCount(event.getJDA());
    }
}
