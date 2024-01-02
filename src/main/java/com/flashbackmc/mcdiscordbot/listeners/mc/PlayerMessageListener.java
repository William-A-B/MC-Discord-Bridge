package com.flashbackmc.mcdiscordbot.listeners.mc;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class PlayerMessageListener implements Listener {
	
	private Logger logger;
	
	private JDA jda;

	private Plugin mcDiscordBot;
	
	
	public PlayerMessageListener(Logger logger, JDA jda, Plugin plugin) {
		this.logger = logger;
		this.jda = jda;
		this.mcDiscordBot = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onMessageSend(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();
		
		String ingameChatChannelID = mcDiscordBot.getConfig().getString("IngameChatChannelID");
		MessageChannel ingameChatChannel = jda.getTextChannelById(ingameChatChannelID);
		
		try {
			ingameChatChannel.sendMessage("**" + player.getName() + "**: " + message).queue();
		}
		catch (IllegalArgumentException exception) {
			Bukkit.getLogger().severe("[MCDiscordBot] " + exception.getStackTrace().toString());
		}	
	}
}
