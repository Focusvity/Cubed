package me.focusvity.cubed.command.fun;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class BlackJack extends CCommand
{

    private Map<String, me.focusvity.cubed.game.blackjack.BlackJack> games = new ConcurrentHashMap<>();

    public BlackJack()
    {
        this.name = "blackjack";
        this.help = "Play a game of Blackjack!";
        this.arguments = "[hit | stand]";
        this.aliases = new String[]{"bj"};
        this.category = Category.FUN;
    }

    @Override
    protected void execute(MessageReceivedEvent event, String[] args)
    {
        TextChannel channel = event.getTextChannel();

        if (args.length == 2)
        {
            if (args[1].equalsIgnoreCase("hit"))
            {
                if (!games.containsKey(userSender.getId()) || !games.get(userSender.getId()).isInProgress())
                {
                    games.put(userSender.getId(), new me.focusvity.cubed.game.blackjack.BlackJack(userSender));
                }

                if (games.get(userSender.getId()).isInProgress() && !games.get(userSender.getId()).isPlayerStanding())
                {
                    games.get(userSender.getId()).hit();
                    reply(games.get(userSender.getId()).toString());
                }
                return;
            }
            else if (args[1].equalsIgnoreCase("stand"))
            {
                if (games.containsKey(userSender.getId()))
                {
                    final Future<?>[] f = {null};

                    if (!games.get(userSender.getId()).isPlayerStanding())
                    {
                        channel.sendMessage(games.get(userSender.getId()).toString()).queue(message ->
                        {
                            games.get(userSender.getId()).stand();
                            f[0] = scheduleRepeat(() ->
                            {
                                boolean didHit = games.get(userSender.getId()).dealerHit();
                                message.editMessage(games.get(userSender.getId()).toString()).queue();
                                if (!didHit)
                                {
                                    games.remove(userSender.getId());
                                    f[0].cancel(false);
                                }
                            }, 2000L, 2000L);
                        });
                        return;
                    }
                }

                reply("You don't have a game running! To start, type `blackjack hit`");
                return;
            }

            reply("Wrong arguments, type `help` for help.");
            return;
        }

        if (games.containsKey(userSender.getId()) && games.get(userSender.getId()).isInProgress())
        {
            reply("You're still in a game! To finish the game, type `blackjack stand`!\n"
                    + games.get(userSender.getId()).toString());
            return;
        }

        reply("You're not playing a game! To start, type `blackjack hit`");
        return;
    }
}
