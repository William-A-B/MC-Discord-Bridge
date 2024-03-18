package com.flashbackmc.mcdiscordbot.listeners.bot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordMessageListener extends ListenerAdapter {

    private JDA jda;
    private Plugin mcDiscordBot;

    public DiscordMessageListener(JDA jda, Plugin plugin) {
        this.jda = jda;
        this.mcDiscordBot = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Don't respond to other bot accounts including this bot
        if (event.getAuthor().isBot()) {
            return;
        }

        // Get the discord channel ID given in the config for relaying the messages
        String ingameChatChannelID = mcDiscordBot.getConfig().getString("IngameChatChannelID");
        // Get the channel someone sent a message in
        MessageChannel channel = event.getChannel();

        // If user sent a message in the ingame chat channel
        if (channel.getId().equals(ingameChatChannelID)) {
            Message message = event.getMessage();
            User user = event.getAuthor();
            String messageContent = message.getContentRaw();
            String userDisplayName = user.getGlobalName();

            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Discord" + ChatColor.DARK_GRAY + "] "
                    + ChatColor.BLUE + userDisplayName + ": " + ChatColor.WHITE + messageContent);
        }
    }
}
