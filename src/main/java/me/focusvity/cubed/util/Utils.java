package me.focusvity.cubed.util;

import me.focusvity.cubed.Cubed;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import okhttp3.*;
import org.json.JSONObject;

import java.awt.*;
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
            channel.sendMessage("Found more than one member mentioned, selecting the first mentioned user").queue();
        }

        return members.get(0);
    }

    public static Role findRole(Message message, TextChannel channel)
    {
        List<Role> roles = message.getMentionedRoles();

        if (roles.isEmpty())
        {
            return null;
        }

        if (roles.size() > 1)
        {
            channel.sendMessage("Found more than one role mentioned, selecting first mentioned role.").queue();
        }

        return roles.get(0);
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

    public static void modLog(Guild guild, ModAction action, MessageEmbed.Field... fields)
    {
        EmbedBuilder embed = new EmbedBuilder().setColor(Color.ORANGE);
        embed.setTitle(action.getName());

        for (MessageEmbed.Field field : fields)
        {
            embed.addField(field);
        }

        TextChannel channel = guild.getTextChannelById(SQLManager.getFromGuilds(guild.getId(), "modlog"));
        if (channel != null)
        {
            channel.sendMessage(embed.build()).queue();
        }
    }
}
