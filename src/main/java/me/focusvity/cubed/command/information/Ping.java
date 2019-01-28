package me.focusvity.cubed.command.information;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ping extends CCommand
{

    public Ping()
    {
        this.name = "ping";
        this.help = "Ping the bot";
        this.category = Category.INFORMATION;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args)
    {
        event.getTextChannel().sendTyping().queue();
        event.getTextChannel().sendMessage("Pinging...").queue(message ->
        {
            message.editMessage(event.getJDA().getPing() + "ms").queue();
        });
    }
}
