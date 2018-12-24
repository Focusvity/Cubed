package me.focusvity.cubed;

import lombok.Getter;
import me.focusvity.cubed.audio.GuildAudioManager;
import me.focusvity.cubed.command.CCommand;
import me.focusvity.cubed.config.Config;
import me.focusvity.cubed.config.IConfig;
import me.focusvity.cubed.listener.MessageListener;
import me.focusvity.cubed.util.SQLManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.Set;

public class Cubed
{

    public static Cubed instance;
    public static JDA api;
    @Getter
    private static Logger logger = LoggerFactory.getLogger(Cubed.class);
    public static GuildAudioManager gam = new GuildAudioManager();
    public static IConfig config;

    public static void main(String[] args)
    {
        if (System.getProperty("file.encoding").equalsIgnoreCase("utf-8"))
        {
            (instance = new Cubed()).loadBot();
        }
        else
        {
            System.exit(-1);
        }
    }

    public void loadBot()
    {
        try
        {
            config = new Config().getIConfig();
            SQLManager manager = new SQLManager(config.getSqlHost(),
                    config.getSqlPort(),
                    config.getSqlUser(),
                    config.getSqlPassword(),
                    config.getSqlDatabase());

            Reflections r = new Reflections("me.focusvity.cubed.command");
            Set<Class<? extends CCommand>> commandClasses = r.getSubTypesOf(CCommand.class);
            for (Class clazz : commandClasses)
            {
                CCommand.getCommands().add((CCommand) clazz.newInstance());
            }

            api = new JDABuilder(config.getToken())
                    .setBulkDeleteSplittingEnabled(false)
                    .setAutoReconnect(true)
                    .addEventListener(new MessageListener())
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .build();
            api.awaitReady();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (LoginException e)
        {
            logger.error("Looks like your login details are missing! Please check 'config.json' to make sure it's filled out!");
        }
        catch (InterruptedException e)
        {
            logger.error("Oops, we got interrupted!");
        }
    }
}
