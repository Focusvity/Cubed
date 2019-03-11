package me.focusvity.cubed.command;

import lombok.Getter;
import me.focusvity.cubed.Cubed;
import me.focusvity.cubed.audio.GuildAudioManager;
import me.focusvity.cubed.blacklist.Blacklist;
import me.focusvity.cubed.blacklist.BlacklistManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class CCommand
{

    public GuildAudioManager gam = Cubed.gam;
    public GuildController controller;
    private ScheduledExecutorService scheduler;

    public CCommand()
    {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @Getter
    private static List<CCommand> commands = new ArrayList<>();

    protected abstract void execute(MessageReceivedEvent event, String[] args);

    @Getter
    protected String name = "null";
    @Getter
    protected String help = "no help";
    @Getter
    protected String arguments = null;
    @Getter
    protected String[] aliases = new String[0];
    @Getter
    protected Category category = Category.INFORMATION;
    protected Permission[] permissions = new Permission[]{};
    protected boolean ownerCommand = false;

    private MessageReceivedEvent event;
    public Member sender;
    public User userSender;

    public void reply(String message)
    {
        event.getTextChannel().sendMessage(message).queue();
    }

    public void reply(MessageEmbed embed)
    {
        event.getTextChannel().sendMessage(embed).queue();
    }

    public ScheduledFuture<?> scheduleRepeat(Runnable task, long start, long repeat)
    {
        return scheduler.scheduleWithFixedDelay(task, start, repeat, TimeUnit.MILLISECONDS);
    }

    public boolean needPermissions()
    {
        return permissions.length > 0;
    }

    public final void run(MessageReceivedEvent event, String[] args)
    {
        this.event = event;
        this.sender = event.getMember();
        userSender = sender.getUser();
        this.controller = event.getGuild().getController();

        // Don't respond to bots
        if (userSender.isBot())
        {
            return;
        }

        if (BlacklistManager.isBlacklisted(userSender.getId()))
        {
            Blacklist blacklist = BlacklistManager.getBlacklist(event.getAuthor().getId());
            PrivateChannel channel = event.getAuthor().openPrivateChannel().complete();
            channel.sendMessage("You have been blacklisted by `"
                    + event.getJDA().getUserById(blacklist.getBy()).getName() + "#"
                    + event.getJDA().getUserById(blacklist.getBy()).getDiscriminator() + "` for"
                    + " `" + blacklist.getReason() + "`").queue();
            channel.sendMessage("You may not use me!").queue();
            return;
        }

        if (!sender.hasPermission(permissions))
        {
            reply("You are missing one or more permissions: "
                    + StringUtils.join(permissions, ", "));
            return;
        }

        if (ownerCommand && !userSender.getId().equals(Cubed.config.getOwnerId()))
        {
            reply("Only the bot owner can run this command!");
            return;
        }

        try
        {
            execute(event, args);
        }
        catch (Exception ex)
        {
            Cubed.getLogger().error("Unable to execute command: " + name, ex);
        }
    }
}
