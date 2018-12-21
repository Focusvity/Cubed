package me.focusvity.cubed.command.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.command.CCommand;

public class Connect extends CCommand
{

    public Connect()
    {
        this.name = "connect";
        this.aliases = new String[]{"join", "summon"};
        this.help = "Connect to the voice channel you are in";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        if (!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect())
        {
            if (event.getMember().getVoiceState().inVoiceChannel())
            {
                event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
                event.reply("Joined `" + event.getMember().getVoiceState().getChannel().getName() + "`!");
            }
            else
            {
                event.reply("You are not in a voice channel!");
            }
        }
        else
        {
            event.getGuild().getAudioManager().closeAudioConnection();
            if (event.getMember().getVoiceState().inVoiceChannel())
            {
                event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
                event.reply("Joined `" + event.getMember().getVoiceState().getChannel().getName() + "`!");
            }
            else
            {
                event.reply("You are not in a voice channel!");
            }
        }
    }
}
