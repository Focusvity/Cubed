package me.focusvity.cubed.command.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.focusvity.cubed.command.CCommand;

public class Pause extends CCommand
{

    public Pause()
    {
        this.name = "pause";
        this.help = "Pause or resume the music!";
        this.aliases = new String[]{"stop", "resume", "continue", "unpause"};
    }

    @Override
    protected void execute(CommandEvent event)
    {
        AudioPlayer player = gam.getMusicManager(event.getGuild()).player;
        if (player.getPlayingTrack() != null)
        {
            if (player.isPaused())
            {
                player.setPaused(false);
                event.reply("Music resumed!");
            }
            else
            {
                player.setPaused(true);
                event.reply("Music paused!");
            }
        }
        else
        {
            event.reply("No music is playing!");
        }
    }
}
