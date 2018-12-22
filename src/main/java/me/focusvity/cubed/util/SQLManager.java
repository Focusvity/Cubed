package me.focusvity.cubed.util;

import lombok.Getter;
import me.focusvity.cubed.Cubed;

import java.io.File;
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

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
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
        c.prepareStatement(blacklist).executeUpdate();
    }
}
