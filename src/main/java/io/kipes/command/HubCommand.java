package io.kipes.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.kipes.HubPlugin;
import net.md_5.bungee.api.ChatColor;

public class HubCommand implements CommandExecutor {

	private HubPlugin plugin;

	public HubCommand(HubPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command comand, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only player can execute this command.");
			return true;
		}
		Player player = (Player) sender;
		if (!player.isOp()) {
			return true;
		}
		if (args[0].equalsIgnoreCase("setspawn")) {
			this.plugin.setNewSpawnLocation(player);
			return true;
		}
		return false;
	}

}
