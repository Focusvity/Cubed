package me.focusvity.cubed.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import lombok.Getter;

public class GuildMusicManager
{

    public final AudioPlayer player;
    public final TrackScheduler scheduler;
    @Getter
    private final AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager)
    {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        sendHandler = new AudioPlayerSendHandler(player);
        player.addListener(scheduler);
    }
}
