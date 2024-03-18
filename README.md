# MC-Discord-Bridge-Plugin
## Introduction
A chat-bridge and server-console bridge plugin for bukkit/spigot for 1.8.
This plugin enables you to relay all chat in a Minecraft server to a Discord channel, as well as relay all Discord messages in that channel to the Minecraft server.
Additionally this plugin has a feature to relay the Minecraft server's console as well as provide the facilty to execute server console commands from a discord channel

## Usage
This plugin requires you setup your own discord bot to run. Make sure the bot has the permission to send messages in the server.
Upon running the plugin for the first time the config files will be initialised, but the plugin won't work. 
Now within the plugins folder you should see a MCDiscordBridge folder which contains a `config.yml` file. 
Within this file, enter the relevant details including:
- `BotToken` - The token for your discord bot 
- `IngameChatChannelID` - The discord channel id that you want to relay the general chat messages to
- `ServerConsoleChannelID` - The discord channel id that you want to relay the server console to (Reccommended that only Admins/members with administrator permissions can see this channel!)
- `StatsChannelID` - The discord channel id that you want the embedded status message to be updated in. This status message displays a live counter of the current number of players online.

## Plugin Info