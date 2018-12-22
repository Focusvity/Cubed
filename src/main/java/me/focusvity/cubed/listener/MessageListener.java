package me.focusvity.cubed.listener;

import me.focusvity.cubed.Cubed;
import me.focusvity.cubed.blacklist.Blacklist;
import me.focusvity.cubed.blacklist.BlacklistManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String message = event.getMessage().getContentRaw();
        User user = event.getAuthor();
        JDA api = event.getJDA();

        if (message.contains("┻━┻"))
        {
            event.getTextChannel().sendMessage("ヘ(´° □°)ヘ┳━┳ PLEASE STOP FLIPPING THE FUCKING TABLES!").queue();
            return;
        }

        if ((message.startsWith(Cubed.config.getDefaultPrefix()) || message.startsWith("~>")) && BlacklistManager.isBlacklisted(user.getId()))
        {
            Blacklist blacklist = BlacklistManager.getBlacklist(user.getId());
            PrivateChannel channel = user.openPrivateChannel().complete();
            channel.sendMessage("You have been blacklisted by `"
                    + api.getUserById(blacklist.getBy()).getName() + "#"
                    + api.getUserById(blacklist.getBy()).getDiscriminator() + "` for"
                    + " `" + blacklist.getReason() + "`").queue();
            channel.sendMessage("You may not use me!").queue();
        }
    }
}
