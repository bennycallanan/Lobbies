package io.kipes.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;

public class MessageUtils {

	private static Chat vaultChat;

	public static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static String getFormattedName(Player player) {
		if (vaultChat == null) {
			RegisteredServiceProvider<Chat> provider = Bukkit.getServicesManager().getRegistration(Chat.class);
			if (provider != null) {
				vaultChat = provider.getProvider();
			}
		}
		String prefix = (vaultChat == null ? "" : vaultChat.getPlayerPrefix(player));
		return color(prefix + player.getName());
	}

	public static void sendMessageToStaff(String msg) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("perm.staff")) {
				player.sendMessage(msg);
			}
		}
	}
}
