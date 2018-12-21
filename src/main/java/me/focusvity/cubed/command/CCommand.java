package me.focusvity.cubed.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.Getter;
import me.focusvity.cubed.Cubed;
import me.focusvity.cubed.audio.GuildAudioManager;

import java.util.ArrayList;
import java.util.List;

public abstract class CCommand extends Command
{

    public GuildAudioManager gam = Cubed.gam;

    @Getter
    private static List<CCommand> commands = new ArrayList<>();

    protected abstract void execute(CommandEvent event);
}
