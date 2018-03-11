package io.kipes.listener;

import java.util.Set;

import io.kipes.util.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import io.kipes.HubPlugin;

public class ChatListener implements Listener {
	
	private HubPlugin plugin;
	
	public ChatListener(HubPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();

		// Handle faction or alliance chat modes.
		Set<Player> recipients = event.getRecipients();

		
		
		if (event.isCancelled()) {
			return;
		}

		// Handle the custom messaging here.
		event.setCancelled(true);

		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(this.getFormattedMessage(player, message, console));

		for (Player recipient : event.getRecipients()) {
			recipient.sendMessage(this.getFormattedMessage(player, message, recipient));
		}
		
	}

	private String getFormattedMessage(Player player, String message, CommandSender viewer) {
		String name = MessageUtils.getFormattedName(player);

		if (player.hasPermission("")) {
			return ChatColor.translateAlternateColorCodes('�', name + "�f: " + message);
		} else {
			return ChatColor.translateAlternateColorCodes('�', name + "�f: " + message);
		}
	}
}