package me.focusvity.cubed.util;

import me.focusvity.cubed.Cubed;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Utils
{

    public static Member findMember(Message message, TextChannel channel)
    {
        List<Member> members = message.getMentionedMembers();

        if (members.isEmpty())
        {
            channel.sendMessage(":x: I couldn't find anyone! Did you mention them?").queue();
            return null;
        }

        if (members.size() > 1)
        {
            channel.sendMessage(":warning: Sorry, too many people!");
            return null;
        }

        return members.get(0);
    }

    public static void updateBotsForDiscordCount(JDA api)
    {
        if (Cubed.config.getBfdToken() == null && Cubed.config.getBfdToken().isEmpty())
        {
            return;
        }

        String url = "https://botsfordiscord.com/api/bot/" + api.getSelfUser().getId();

        JSONObject object = new JSONObject();
        object.put("server_count", api.getGuilds().size());

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), object.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("User-Agent", "DiscordBot Cubed")
                .addHeader("Authorization", Cubed.config.getBfdToken())
                .build();

        try
        {
            Response response = new OkHttpClient().newCall(request).execute();

            if (response.isSuccessful())
            {
                Cubed.getLogger().info("Successfully sent data to Bots For Discord");
            }
            else
            {
                Cubed.getLogger().error("Error sending data to Bots For Discord: " + response.message());
            }

            response.close();
        }
        catch (IOException ex)
        {
            Cubed.getLogger().error("", ex);
        }
    }
}
