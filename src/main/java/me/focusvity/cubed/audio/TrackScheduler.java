package me.focusvity.cubed.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.LinkedList;
import java.util.Queue;

public class TrackScheduler extends AudioEventAdapter
{

    public final Queue<AudioTrack> queue;
    private final AudioPlayer player;

    public TrackScheduler(AudioPlayer player)
    {
        this.player = player;
        this.queue = new LinkedList<>();
    }

    public void queue(AudioTrack track)
    {
        if (!player.startTrack(track, true))
        {
            queue.offer(track);
        }
    }

    public void nextTrack()
    {
        if (queue.peek() == null)
        {
            return;
        }

        AudioTrack track = queue.poll();

        if (track != null)
        {
            player.startTrack(track, false);
            return;
        }

        if (player.getPlayingTrack() != null)
        {
            player.stopTrack();
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason reason)
    {
        if (reason.mayStartNext)
        {
            nextTrack();
        }
    }
}
