package com.flashbackmc.mcdiscordbot;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.dv8tion.jda.api.JDA;



public class LogAppender extends AbstractAppender {

	private String messages = "";
    private String messagesCont = "";
    private String messagesCont2 = "";
	private final Plugin mcDiscordBot;
    private final JDA jda;
	
	
	@SuppressWarnings("deprecation")
	public LogAppender(Plugin plugin, JDA jda) {
		super("MyConsoleOutput" + System.currentTimeMillis(), null, null);
		this.mcDiscordBot = plugin;
		this.jda = jda;
		start();
	}


	@Override
	public void append(LogEvent event) {
		// you can get only the log message like this:
        String message = event.getMessage().getFormattedMessage();

        // and you can construct your whole log message like this:
        message = "[" + java.time.LocalTime.now() + " " + event.getLevel().toString() + "]: " + message + "\n";
        messages += message;
	}
	
	public void sendMessages() {

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (messages.length() != 0) {
                        messages = messages.replaceAll("\u001B\\[[;\\d]*m", "");
                        
                        if(messages.length() > 2000) {
                            // String messageTooLong = "\n\nThis message has exceeded the discord message limit (2000 characters) so the rest has been cut out. To see it completely please check the console itself!";
                            // messages = messages.substring(0, (1999-messageTooLong.length())-6);
                            // messages += messageTooLong;
                            if (messages.length() >= 3986 && messages.length() < 5979) {
                                messagesCont2 = messages.substring(3986, messages.length());
                                messagesCont = messages.substring(1993, 3986);                             
                            }
                            else if (messages.length() >= 1993 && messages.length() < 3986){
                                messagesCont = messages.substring(1993, messages.length());
                            }
                            
                            messages = messages.substring(0, 1993);
                        }
                        
                        String consoleChannelID = mcDiscordBot.getConfig().getString("ServerConsoleChannelID");
                        try {
                        	jda.getTextChannelById(consoleChannelID).sendMessage("```" + messages + "```").queue();
                        }
                        catch (NumberFormatException numberFormatException) {
                        	Bukkit.getLogger().severe("[DiscordServerConsole] Invalid channel ID '" + consoleChannelID + "'! Make sure to put a valid channel ID in the config file! Without this the plugin won't work properly! Disabling plugin...");
                            cancel();
                        }
                        if (messagesCont.length() > 0) {
                            try {
                                jda.getTextChannelById(consoleChannelID).sendMessage("```" + messagesCont + "```").queue();
                            }
                            catch (NumberFormatException numberFormatException) {
                                Bukkit.getLogger().severe("[DiscordServerConsole] Invalid channel ID '" + consoleChannelID + "'! Make sure to put a valid channel ID in the config file! Without this the plugin won't work properly! Disabling plugin...");
                                cancel();
                            }
                        }
                        
                        if (messagesCont2.length() > 0) {
                            try {
                                jda.getTextChannelById(consoleChannelID).sendMessage("```" + messagesCont2 + "```").queue();
                            }
                            catch (NumberFormatException numberFormatException) {
                                Bukkit.getLogger().severe("[DiscordServerConsole] Invalid channel ID '" + consoleChannelID + "'! Make sure to put a valid channel ID in the config file! Without this the plugin won't work properly! Disabling plugin...");
                                cancel();
                            }
                        }
                        
                    }
                } catch (NullPointerException e) {

                }
                messages = "";
                messagesCont = "";
                messagesCont2 = "";
            }
        }.runTaskTimer(mcDiscordBot, 0L, 20L);
    }
	
}
