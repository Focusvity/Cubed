package me.focusvity.cubed.command.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.focusvity.cubed.command.CCommand;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Eval extends CCommand
{

    private ScriptEngine engine;

    public Eval()
    {
        this.name = "eval";
        this.aliases = new String[]{"evaluate"};
        this.arguments = "<code>";
        this.help = "Evaluates the entered code";
        this.ownerCommand = true;
        engine = new ScriptEngineManager().getEngineByName("Nashorn");
    }

    @Override
    protected void execute(CommandEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");

        try
        {
            engine.put("event", event);
            engine.put("message", event.getMessage());
            engine.put("channel", event.getChannel());
            engine.put("args", args);
            engine.put("api", event.getJDA());
            engine.put("guild", event.getGuild());
            engine.put("member", event.getMember());

            Object out = engine.eval(StringUtils.join(args, " ", 1, args.length));

            event.reply(out == null ? ":white_check_mark: Executed without any errors!" : out.toString());
        }
        catch (ScriptException e)
        {
            event.reply(e.getMessage());
        }
    }
}
