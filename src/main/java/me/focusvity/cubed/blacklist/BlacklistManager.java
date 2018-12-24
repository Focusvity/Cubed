package me.focusvity.cubed.blacklist;

import lombok.Getter;
import me.focusvity.cubed.Cubed;
import me.focusvity.cubed.util.SQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BlacklistManager
{

    @Getter
    private static Map<String, Blacklist> blacklistMap = new HashMap<>();

    public BlacklistManager()
    {
        init();
    }

    public void init()
    {
        blacklistMap.clear();
        Connection c = SQLManager.getConnection();
        try
        {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM blacklist");
            ResultSet result = statement.executeQuery();
            while (result.next())
            {
                String id = result.getString("id");
                String by = result.getString("by");
                String reason = result.getString("reason");
                blacklistMap.put(id, new Blacklist(id, by, reason));
            }
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }

        Cubed.getLogger().info("Successfully loaded " + blacklistMap.size() + " blacklisted users");
    }

    public static void addBlacklist(String id, String by, String reason)
    {
        if (isBlacklisted(id))
        {
            return;
        }

        Blacklist blacklist = new Blacklist(id, by, reason);
        blacklistMap.put(id, blacklist);
        addToSql(id, by, reason);
    }

    public static void removeBlacklist(String id)
    {
        if (!isBlacklisted(id))
        {
            return;
        }

        blacklistMap.remove(id);
        deleteFromSql(id);
    }

    public static boolean isBlacklisted(String id)
    {
        for (Blacklist blacklist : blacklistMap.values())
        {
            if (blacklist.getId().equals(id))
            {
                return true;
            }
        }
        return false;
    }

    public static Blacklist getBlacklist(String id)
    {
        return blacklistMap.get(id);
    }

    public static void deleteFromSql(String id)
    {
        Connection c = SQLManager.getConnection();
        try
        {
            c.prepareStatement("DELETE FROM blacklist WHERE `id` = " + id).executeUpdate();
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }

    public static void addToSql(String id, String by, String reason)
    {
        Connection c = SQLManager.getConnection();
        try
        {
            PreparedStatement statement = c.prepareStatement("INSERT INTO blacklist (`id`, `by`, reason) VALUES(?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, by);
            statement.setString(3, reason);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }
}
