package me.focusvity.cubed.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.ArrayList;
import java.util.List;

public class AudioLoader implements AudioLoadResultHandler
{

    private final TextChannel channel;
    private final String trackUrl;
    private final GuildMusicManager manager;
    private final boolean addPlaylist;

    public AudioLoader(TextChannel channel, String trackUrl, GuildMusicManager manager, boolean addPlaylist)
    {
        this.channel = channel;
        this.trackUrl = trackUrl;
        this.addPlaylist = addPlaylist;
        this.manager = manager;
    }

    @Override
    public void trackLoaded(AudioTrack track)
    {
        connectToFirstVoiceChannel(channel.getGuild().getAudioManager());
        String message = "Adding to queue: `" + track.getInfo().title + "`";
        if (manager.player.getPlayingTrack() == null)
        {
            message += "\nMusic started.";
        }

        manager.scheduler.queue(track);
        channel.sendMessage(message).queue();
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist)
    {
        connectToFirstVoiceChannel(channel.getGuild().getAudioManager());
        AudioTrack firstTrack = playlist.getSelectedTrack();
        List<AudioTrack> tracks = new ArrayList<>();

        playlist.getTracks().forEach(t ->
        {
            tracks.add(t);
        });

        if (tracks.isEmpty())
        {
            channel.sendMessage("This playlist is empty!");
            return;
        }
        else if (firstTrack == null)
        {
            firstTrack = playlist.getTracks().get(0);
        }

        if (addPlaylist)
        {
            tracks.forEach(manager.scheduler::queue);
        }
        else
        {
            manager.scheduler.queue(firstTrack);
        }

        String message;
        if (addPlaylist)
        {
            message = "Adding `" + playlist.getTracks().size() + "` tracks to queue from playlist: `" + playlist.getName() + "`";
            if (manager.player.getPlayingTrack() == null)
            {
                message += "\nMusic started.";
            }
        }
        else
        {
            message = "Adding to queue `" + firstTrack.getInfo().title + "`. (First track of playlist: `" + playlist.getName() + "`)";
            if (manager.player.getPlayingTrack() == null)
            {
                message += "\nMusic started.";
            }
        }

        channel.sendMessage(message).queue();
    }

    @Override
    public void noMatches()
    {
        channel.sendMessage("Nothing found by `" + trackUrl + "`").queue();
    }

    @Override
    public void loadFailed(FriendlyException ex)
    {
        channel.sendMessage("Could not play `" + trackUrl + "` because " + ex.getMessage()).queue();
    }

    private void connectToFirstVoiceChannel(AudioManager audioManager)
    {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect())
        {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels())
            {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }
}
