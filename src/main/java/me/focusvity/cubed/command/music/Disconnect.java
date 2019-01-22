package me.focusvity.cubed.command.music;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Disconnect extends CCommand
{

    public Disconnect()
    {
        this.name = "disconnect";
        this.aliases = new String[]{"leave"};
        this.help = "Disconnect from a voice channel";
        this.category = Category.MUSIC;
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        if (event.getGuild().getAudioManager().isConnected())
        {
            event.getGuild().getAudioManager().closeAudioConnection();
            reply("Left the voice channel!");
        }
        else
        {
            reply("I am not in a voice channel!");
        }
    }
}
