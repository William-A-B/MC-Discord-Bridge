package com.flashbackmc.mcdiscordbot.util;

import org.bukkit.entity.Player;

import com.avaje.ebean.common.BootupEbeanManager;

import org.bukkit.Bukkit;

import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;
import java.util.HashSet;
import java.util.Hashtable;

public class StatsEmbedBuilder {
    
    private EmbedBuilder statsEmbed;
	private Boolean quitEvent;

    public StatsEmbedBuilder(Boolean quitEvent) {
        this.quitEvent = quitEvent;
		setupStatsEmbed();
    }



    private void setupStatsEmbed() {

        //HashSet<Player> players = new HashSet<Player>(Bukkit.getServer().getOnlinePlayers());


        int onlinePlayers = Bukkit.getServer().getOnlinePlayers().size();
        int maxPlayers = Bukkit.getServer().getMaxPlayers();
        //players = Bukkit.getServer().getOnlinePlayers();

		if (quitEvent == true) {
			if (onlinePlayers != 0) {
				onlinePlayers = onlinePlayers - 1;
			}
		}

        // Create the EmbedBuilder instance
		this.statsEmbed = new EmbedBuilder();

		/*
			Set the title:
			1. Arg: title as string
			2. Arg: URL as string or could also be null
		*/
		statsEmbed.setTitle("**FBMCCTF Server Stats**", null);

		/*
			Set the color
		*/
		statsEmbed.setColor(new Color(147, 198, 51));

		/*
			Set the text of the Embed:
			Arg: text as string
		*/
		statsEmbed.setDescription("**Online Players:** " + "(" + onlinePlayers + "/" + maxPlayers + ")");

		/*
			Add fields to embed:
			1. Arg: title as string
			2. Arg: text as string
			3. Arg: inline mode true / false
		*/
		//statsEmbed.addField("Players Online", players.get, false);

		/*
			Add spacer like field
			Arg: inline mode true / false
		*/
		//statsEmbed.addBlankField(false);

		/*
			Add embed author:
			1. Arg: name as string
			2. Arg: url as string (can be null)
			3. Arg: icon url as string (can be null)
		*/
		statsEmbed.setAuthor("FBCCTF", null, "https://i.imgur.com/n8KWbIJ.png");

		/*
			Set footer:
			1. Arg: text as string
			2. icon url as string (can be null)
		*/
		//statsEmbed.setFooter("Text", "https://github.com/zekroTJA/DiscordBot/blob/master/.wstatsEmbedsrc/zekroBot_Logo_-_round_small.png");

		/*
			Set image:
			Arg: image url as string
		*/
		//statsEmbed.setImage("https://github.com/zekroTJA/DiscordBot/blob/master/.wstatsEmbedsrc/logo%20-%20title.png");

		/*
			Set thumbnail image:
			Arg: image url as string
		*/
		statsEmbed.setThumbnail("https://i.imgur.com/n8KWbIJ.png");

    }

    public EmbedBuilder getStatsEmbed() {
        return statsEmbed;
    }

}
