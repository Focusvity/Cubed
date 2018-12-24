package me.focusvity.cubed.listener;

import me.focusvity.cubed.Cubed;
import me.focusvity.cubed.command.CCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String message = event.getMessage().getContentRaw();

        if (message.contains("┻━┻"))
        {
            event.getTextChannel().sendMessage("ヘ(´° □°)ヘ┳━┳ PLEASE STOP FLIPPING THE FUCKING TABLES!").queue();
            return;
        }

        int position = Cubed.config.getDefaultPrefix().length();
        String[] args = message.split(" ");
        if (message.startsWith(Cubed.config.getDefaultPrefix()))
        {
            for (CCommand command : CCommand.getCommands())
            {
                if (message.substring(position).startsWith(command.getName()))
                {
                    command.run(event, args);
                    return;
                }
                else
                {
                    for (String alias : command.getAliases())
                    {
                        if (message.substring(position).startsWith(alias))
                        {
                            command.run(event, args);
                            return;
                        }
                    }
                }
            }
        }
    }
}
