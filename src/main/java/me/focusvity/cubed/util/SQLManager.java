package me.focusvity.cubed.util;

import com.mysql.jdbc.Driver;
import lombok.Getter;
import me.focusvity.cubed.Cubed;
import net.dv8tion.jda.core.entities.Guild;

import java.sql.*;

public class SQLManager
{

    @Getter
    private static Connection connection;

    public SQLManager(String host, int port, String user, String password, String database)
    {
        try
        {
            String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);

            // Password can't be null
            if (password == null)
            {
                password = "";
            }

            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null)
            {
                connection.setAutoCommit(true);
                generateTable();
                Cubed.getLogger().info("Connection to MySQL established!");
            }
            else
            {
                Cubed.getLogger().warn("Connection to MySQL failed!");
            }
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }

    public static void updateTable(String table, String where, String id, String column, Object change)
    {
        Connection c = getConnection();
        try
        {
            PreparedStatement statement = c.prepareStatement("UPDATE " + table + " SET `" + column + "` = ? "
                    + "WHERE " + where + " = ?");
            statement.setObject(1, change);
            statement.setString(2, id);
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }

    public static Object getFromTable(String table, String where, String id, String column)
    {
        Connection c = getConnection();
        try
        {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM " + table + " WHERE `" + where
                    + "` = ?");
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next())
            {
                return result.getObject(column);
            }
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
        return null;
    }

    public static String getFromGuilds(String id, String column)
    {
        Object object = getFromTable("guilds", "id", id, column);
        if (object instanceof String)
        {
            return (String) object;
        }
        return null;
    }

    public static void generateNewGuild(Guild guild)
    {
        Connection c = getConnection();
        try
        {
            PreparedStatement statement = c.prepareStatement("INSERT INTO guilds (`id`) VALUES (?)");
            statement.setString(1, guild.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }

    public static void deleteGuild(Guild guild)
    {
        Connection c = getConnection();
        try
        {
            PreparedStatement statement = c.prepareStatement("DELETE FROM guilds WHERE `id` = ?");
            statement.setString(1, guild.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }

    public static boolean guildExists(Guild guild)
    {
        return getFromTable("guilds", "id", guild.getId(), "id") != null;
    }

    public static void generateTable() throws SQLException
    {
        Connection c = getConnection();
        String blacklist = "CREATE TABLE IF NOT EXISTS blacklist ("
                + "`id` VARCHAR(255) PRIMARY KEY,"
                + "`by` VARCHAR(255) NOT NULL,"
                + "reason TEXT"
                + ")";
        String guilds = "CREATE TABLE IF NOT EXISTS guilds ("
                + "`id` VARCHAR(255) PRIMARY KEY,"
                + "autorole VARCHAR(255),"
                + "cmdprefix VARCHAR(255),"
                + "membercount VARCHAR(255),"
                + "joinchannel VARCHAR(255),"
                + "modlog VARCHAR(255)"
                + ")";
        c.prepareStatement(blacklist).executeUpdate();
        c.prepareStatement(guilds).executeUpdate();
    }
}
