package me.focusvity.cubed.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

public class Play extends CCommand
{

    public Play()
    {
        this.name = "play";
        this.arguments = "(title/link)";
        this.help = "Play a song!";
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        AudioPlayer player = gam.getMusicManager(event.getGuild()).player;

        if (args.length == 1)
        {
            if (player.isPaused() && player.getPlayingTrack() != null)
            {
                player.setPaused(false);
                reply("Music resumed!");
            }
            else if (gam.getMusicManager(event.getGuild()).scheduler.queue.isEmpty())
            {
                reply("I couldn't play anything, the queue is empty!");
                return;
            }
        }

        String rawString = StringUtils.join(args, " ", 1, args.length);
        String string;

        if (rawString.startsWith("<") && rawString.endsWith(">"))
        {
            string = rawString.substring(1, rawString.length() - 1);
        }
        else
        {
            string = rawString;
        }

        boolean search = true;
        if (string.startsWith("http://") || string.startsWith("https://"))
        {
            search = false;
        }

        gam.loadAndPlay(event.getTextChannel(), (search ? "ytsearch:" + string : string), false);
        if (player.isPaused())
        {
            player.setPaused(false);
        }
    }
}
