package me.focusvity.cubed.command.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.command.CCommand;

public class Disconnect extends CCommand
{

    public Disconnect()
    {
        this.name = "disconnect";
        this.aliases = new String[]{"leave"};
        this.help = "Disconnect from a voice channel";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (event.getGuild().getAudioManager().isConnected())
        {
            event.getGuild().getAudioManager().closeAudioConnection();
            event.reply("Left the voice channel!");
        }
        else
        {
            event.reply("I am not in a voice channel!");
        }
    }
}
