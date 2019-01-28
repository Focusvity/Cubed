package me.focusvity.cubed.util;

import com.mysql.jdbc.Driver;
import lombok.Getter;
import me.focusvity.cubed.Cubed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static void generateTable() throws SQLException
    {
        Connection c = getConnection();
        String blacklist = "CREATE TABLE IF NOT EXISTS blacklist ("
                + "`id` VARCHAR(255) PRIMARY KEY,"
                + "`by` VARCHAR(255) NOT NULL,"
                + "reason TEXT)";
        String guilds = "CREATE TABLE IF NOT EXISTS guilds ("
                + "`id` VARCHAR(255) PRIMARY KEY,"
                + "autorole VARCHAR(255),"
                + "cmdprefix VARCHAR(255))";
        c.prepareStatement(blacklist).executeUpdate();
        c.prepareStatement(guilds).executeUpdate();
    }
}
