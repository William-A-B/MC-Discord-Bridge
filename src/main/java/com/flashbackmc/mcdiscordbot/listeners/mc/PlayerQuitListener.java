package com.flashbackmc.mcdiscordbot.listeners.mc;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.flashbackmc.mcdiscordbot.MCDiscordBot;
import com.flashbackmc.mcdiscordbot.util.StatsEmbedBuilder;

import com.flashbackmc.mcdiscordbot.util.StatsEmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;

public class PlayerQuitListener implements Listener {
	
	private Logger logger;
	
	private JDA jda;

	private Plugin mcDiscordBot;
	
	
	public PlayerQuitListener(Logger logger, JDA jda, Plugin plugin) {
		this.logger = logger;
		this.jda = jda;
		this.mcDiscordBot = plugin;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		int onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
		//int onlinePlayers = Bukkit.getServer().getOnlinePlayers().length;
		int maxPlayers = Bukkit.getServer().getMaxPlayers();

		//String statsChannelID = mcDiscordBot.getConfig().getString("StatsChannelID");;
		//TextChannel statsChannel = jda.getTextChannelById(statsChannelID);
		
		String ingameChatChannelID = mcDiscordBot.getConfig().getString("IngameChatChannelID");
		MessageChannel ingameChatChannel = jda.getTextChannelById(ingameChatChannelID);
		
		//updateStatsChannel(statsChannel);
		
		if (onlinePlayers != 0) {
			onlinePlayers = onlinePlayers - 1;
		}
		
		try {
			ingameChatChannel.sendMessage("**" + player.getName() + "** quit the game! (" + onlinePlayers + "/" + maxPlayers + ")").queue();
		}
		catch (IllegalArgumentException exception) {
			Bukkit.getLogger().severe("[MCDiscordBot] " + exception.getStackTrace().toString());
		}
		
	}
	
	private void updateStatsChannel(TextChannel statsChannel) {

		StatsEmbedBuilder statsEmbed = new StatsEmbedBuilder(true);

		List<Message> msgs;

        msgs = statsChannel.getHistory().retrievePast(1).complete();
        if (msgs.size() != 0) {
			msgs.get(0).delete().queue();
		}

		statsChannel.sendMessageEmbeds(statsEmbed.getStatsEmbed().build()).queue();
	}
	
}