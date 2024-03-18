package com.flashbackmc.mcdiscordbot.listeners.bot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    private Plugin mcDiscordBot;

    public MessageReceivedListener(Plugin plugin) {
        this.mcDiscordBot = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Don't respond to other bot accounts including this bot
        if (event.getAuthor().isBot()) {
            return;
        }

        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        String content = message.getContentRaw();
        String user = event.getAuthor().getAsMention();

        if (content.equals("!ping")) {
            channel.sendMessage("Pong!").queue();
        }
        else if (content.equals("!ip")) {
            channel.sendMessage(user + " Our IP is `play.flashbackmc.net` :-)").queue();
        }
        else if (content.equals("!online")) {

            if (event.getChannel().getId().equalsIgnoreCase(mcDiscordBot.getConfig().getString("ChatChannelID"))) {

                int numOnlinePlayers = Bukkit.getServer().getOnlinePlayers().size();

                channel.sendMessage("**There are " + numOnlinePlayers + " players online currently.**").queue();
            }
        }
//		else if (content.equals("!list")) {
//			if (event.getChannel().getId().equalsIgnoreCase(mcDiscordBot.getConfig().getString("ChatChannelID"))) {
//				Object[] onlinePlayers = Bukkit.getOnlinePlayers().toArray();
//				channel.sendMessage("```\n```").queue();
//			}
//		}

    }

}
