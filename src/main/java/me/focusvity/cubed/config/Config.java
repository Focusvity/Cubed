package me.focusvity.cubed.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.focusvity.cubed.Cubed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config
{

    @Getter
    private static Config instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path configPath = new File(".").toPath().resolve("config.json");
    @Getter
    private IConfig iConfig;

    public Config()
    {
        if (!configPath.toFile().exists())
        {
            Cubed.getLogger().warn("Missing configuration file! Generating a fresh batch...");
            Cubed.getLogger().warn("Remember to fill out the configuration file before starting!");
            iConfig = generate();
            save();
            System.exit(12);
            return;
        }

        load();
        Cubed.getLogger().info("The config file has successfully loaded!");
    }

    private IConfig generate()
    {
        IConfig c = new IConfig();
        c.setToken("");
        c.setOwnerId("");
        c.setDefaultPrefix("");
        return c;
    }

    public void save()
    {
        String json = gson.toJson(iConfig);
        try
        {
            BufferedWriter bw = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8);
            bw.write(json);
            bw.close();
        }
        catch (IOException ex)
        {
            Cubed.getLogger().error("Unable to save config...", ex);
        }
    }

    public void load()
    {
        try
        {
            BufferedReader br = Files.newBufferedReader(configPath, StandardCharsets.UTF_8);
            iConfig = gson.fromJson(br, IConfig.class);
            br.close();
            check();
        }
        catch (IOException ex)
        {
            Cubed.getLogger().error("Unable to load config...", ex);
        }
    }

    private void check()
    {
        boolean modified = false;

        if (iConfig.getToken() == null)
        {
            iConfig.setToken("");
            modified = true;
        }

        if (iConfig.getOwnerId() == null)
        {
            iConfig.setOwnerId("");
            modified = true;
        }

        if (iConfig.getDefaultPrefix() == null)
        {
            iConfig.setDefaultPrefix("");
            modified = true;
        }

        if (iConfig.getSqlHost() == null)
        {
            iConfig.setSqlHost("127.0.0.1");
            modified = true;
        }

        if (iConfig.getSqlPort() < 0)
        {
            iConfig.setSqlPort(3306);
            modified = true;
        }

        if (iConfig.getSqlUser() == null)
        {
            iConfig.setSqlUser("root");
            modified = true;
        }

        if (iConfig.getSqlPassword() == null)
        {
            iConfig.setDefaultPrefix("");
            modified = true;
        }

        if (iConfig.getBfdToken() == null)
        {
            iConfig.setBfdToken("");
            modified = true;
        }

        if (modified)
        {
            save();
        }
    }
}
