package com.flashbackmc.mcdiscordbot.listeners.bot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ConsoleCommandListener extends ListenerAdapter {

    private Plugin mcDiscordBot;

    public ConsoleCommandListener(Plugin plugin) {
        this.mcDiscordBot = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Don't respond to other bot accounts including this bot
        if (event.getAuthor().isBot()) {
            return;
        }

        String consoleChannelID = mcDiscordBot.getConfig().getString("ServerConsoleChannelID");

        Message message = event.getMessage();
        String messageContent = message.getContentRaw();
        String consoleOutput;

        MessageChannel channel = event.getChannel();

        if (channel.getId().equalsIgnoreCase(consoleChannelID)) {

            if (messageContent.equals(">")) {
                channel.sendMessage("No command arguments provided!\nPlease use `>command here`").queue();
                return;
            }
            else if (messageContent.equals(">reload")) {
                channel.sendMessage("This command cannot be run from Discord!").queue();
                return;
            }
            else if (messageContent.startsWith(">")) {
                String consoleCommand = messageContent.substring(1);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), consoleCommand);
//					consoleOutput = ConsoleOutput.execCmd(messageContent);
//					
//					channel.sendMessage(consoleOutput).queue();
            }
            else {
                return;
            }
        }

    }

}
