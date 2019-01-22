package me.focusvity.cubed.command.music;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Connect extends CCommand
{

    public Connect()
    {
        this.name = "connect";
        this.aliases = new String[]{"join", "summon"};
        this.help = "Connect to the voice channel you are in";
        this.category = Category.MUSIC;
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        if (!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect())
        {
            if (event.getMember().getVoiceState().inVoiceChannel())
            {
                event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
                reply("Joined `" + event.getMember().getVoiceState().getChannel().getName() + "`!");
            }
            else
            {
                reply("You are not in a voice channel!");
            }
        }
        else
        {
            event.getGuild().getAudioManager().closeAudioConnection();
            if (event.getMember().getVoiceState().inVoiceChannel())
            {
                event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
                reply("Joined `" + event.getMember().getVoiceState().getChannel().getName() + "`!");
            }
            else
            {
                reply("You are not in a voice channel!");
            }
        }
    }
}
