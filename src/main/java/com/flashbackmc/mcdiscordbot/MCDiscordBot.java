package com.flashbackmc.mcdiscordbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.flashbackmc.mcdiscordbot.listeners.bot.ConsoleCommandListener;
import com.flashbackmc.mcdiscordbot.listeners.bot.DiscordMessageListener;
import com.flashbackmc.mcdiscordbot.listeners.bot.MessageReceivedListener;
import com.flashbackmc.mcdiscordbot.listeners.mc.PlayerJoinListener;
import com.flashbackmc.mcdiscordbot.listeners.mc.PlayerMessageListener;
import com.flashbackmc.mcdiscordbot.listeners.mc.PlayerQuitListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class MCDiscordBot extends JavaPlugin {

	private String botToken;
	private JDA jda;
	private LogAppender appender;
	private org.apache.logging.log4j.core.Logger logger;
	
	
	public MCDiscordBot() {
		this.botToken = null;
		this.jda = null;
		
	}
	

	
	@Override
	public void onEnable() {
		System.out.println("****************************");
		System.out.println("Enabling MCDiscordBot Plugin");
		System.out.println("****************************");
		
		setupConfig();
		
		
		
		
		try {
			JDABuilder builder = JDABuilder.createDefault(botToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
			builder.setStatus(OnlineStatus.ONLINE);
			builder.setActivity(Activity.listening("FBMCCTF"));
			this.jda = builder.build();
			
            Bukkit.getLogger().info("[DiscordServerConsole] The bot is now online!");

            appender = new LogAppender(this, this.jda);
            try {
                logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
                logger.addAppender(appender);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            appender.sendMessages();
        }
		catch (InvalidTokenException | IllegalArgumentException e) {
            Bukkit.getLogger().severe("[DiscordServerConsole] The token in the config file is invalid! Please change the token in the config and restart/reload the server to apply the changes.");
            Bukkit.getLogger().info("[DiscordServerConsole] Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
		
		
		
		setupMCListeners();
		setupBotListeners();
		
	}

	public JDA getJDA() {
		return jda;
	}

	@Override
	public void onDisable() {
		System.out.println("*****************************");
		System.out.println("Disabling MCDiscordBot Plugin");
		System.out.println("*****************************");
		
		
		if(jda != null) {
            logger.removeAppender(appender);
            appender.stop();
        }
	}
	
	

	
	private void setupConfig() {
		this.getConfig().options().copyDefaults();
		this.saveDefaultConfig();

		this.botToken = this.getConfig().getString("BotToken");
		
	}
	
	private void setupMCListeners() {
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(getLogger(), jda, this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(getLogger(), jda, this), this);
		getServer().getPluginManager().registerEvents(new PlayerMessageListener(getLogger(), jda, this), this);
		
		
	}
	
	private void setupBotListeners() {
		jda.addEventListener(new MessageReceivedListener(this));
		jda.addEventListener(new DiscordMessageListener(jda, this));
		jda.addEventListener(new ConsoleCommandListener(this));
	}
}
