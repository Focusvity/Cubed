package me.focusvity.cubed.command.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Pause extends CCommand
{

    public Pause()
    {
        this.name = "pause";
        this.help = "Pause or resume the music!";
        this.aliases = new String[]{"stop", "resume", "continue", "unpause"};
        this.category = Category.MUSIC;
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        AudioPlayer player = gam.getMusicManager(event.getGuild()).player;
        if (player.getPlayingTrack() != null)
        {
            if (player.isPaused())
            {
                player.setPaused(false);
                reply("Music resumed!");
            }
            else
            {
                player.setPaused(true);
                reply("Music paused!");
            }
        }
        else
        {
            reply("No music is playing!");
        }
    }
}
