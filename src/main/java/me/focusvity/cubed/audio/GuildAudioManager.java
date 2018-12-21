package me.focusvity.cubed.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

import java.util.HashMap;
import java.util.Map;

public class GuildAudioManager
{

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> managers;

    public GuildAudioManager()
    {
        this.managers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();

        YoutubeAudioSourceManager yasm = new YoutubeAudioSourceManager(true);
        yasm.configureRequests(config -> RequestConfig.copy(config).setCookieSpec(CookieSpecs.IGNORE_COOKIES).build());

        playerManager.registerSourceManager(yasm);
        playerManager.registerSourceManager(new HttpAudioSourceManager());

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getMusicManager(Guild guild)
    {
        GuildMusicManager musicManager = managers.get(guild.getIdLong());

        if (musicManager == null)
        {
            musicManager = new GuildMusicManager(playerManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl, boolean addPlaylist)
    {
        GuildMusicManager musicManager = getMusicManager(channel.getGuild());
        AudioLoader loader = new AudioLoader(channel, trackUrl, musicManager, addPlaylist);
        playerManager.loadItemOrdered(musicManager, trackUrl, loader);
    }
}
