package me.focusvity.cubed.command.fun;

import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.command.Category;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
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
        User user = event.getAuthor();
        TextChannel channel = event.getTextChannel();

        if (args.length == 2)
        {
            if (args[1].equalsIgnoreCase("hit"))
            {
                if (!games.containsKey(user.getId()) || !games.get(user.getId()).isInProgress())
                {
                    games.put(user.getId(), new me.focusvity.cubed.game.blackjack.BlackJack(user));
                }

                if (games.get(user.getId()).isInProgress() && !games.get(user.getId()).isPlayerStanding())
                {
                    games.get(user.getId()).hit();
                    reply(games.get(user.getId()).toString());
                }
                return;
            }
            else if (args[1].equalsIgnoreCase("stand"))
            {
                if (games.containsKey(user.getId()))
                {
                    final Future<?>[] f = {null};

                    if (!games.get(user.getId()).isPlayerStanding())
                    {
                        channel.sendMessage(games.get(user.getId()).toString()).queue(message ->
                        {
                            games.get(user.getId()).stand();
                            f[0] = scheduleRepeat(() ->
                            {
                                boolean didHit = games.get(user.getId()).dealerHit();
                                message.editMessage(games.get(user.getId()).toString()).queue();
                                if (!didHit)
                                {
                                    games.remove(user.getId());
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
        }

        if (games.containsKey(user.getId()) && games.get(user.getId()).isInProgress())
        {
            reply("You're still in a game! To finish the game, type `blackjack stand`!\n"
                    + games.get(user.getId()).toString());
            return;
        }

        reply("You're not playing a game! To start, type `blackjack hit`");
        return;
    }
}
