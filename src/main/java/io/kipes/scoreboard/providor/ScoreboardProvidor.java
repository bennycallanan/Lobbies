package io.kipes.scoreboard.providor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.kipes.scoreboard.ScoreboardHelper;
import io.kipes.util.EzQueueUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import io.kipes.HubPlugin;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardProvidor implements Listener {

	private HubPlugin plugin;
	private Map<Player, ScoreboardHelper> helpers = new HashMap<Player, ScoreboardHelper>();

	public ScoreboardProvidor(HubPlugin plugin) {
		this.plugin = plugin;
	}

	public void init() {
		new BukkitRunnable() {

			@Override
			public void run() {
				HubPlugin.getPlugin().getCount("ALL");
				for (Player online : Bukkit.getOnlinePlayers()) {
					ScoreboardHelper helper = HubPlugin.getPlugin().getScoreboardManager().getScoreboardFor(online);
					helper.clear();
					List<String> results = new ArrayList<String>();
					for (String string : HubPlugin.getPlugin().getConfig().getStringList("scoreboard.display")) {
						String result = ScoreboardProvidor.this.replaceAll(online, string);
						if (result != null) {
							result = ChatColor.translateAlternateColorCodes('&', result);
							results.add(result);
						}
					}
					if (!isOnlySpacer(results)) {
						for (String result : results) {
							result = result.replace("%spacer%", " ");
							result = result.replace("%bar%",
									HubPlugin.getPlugin().getConfig().getString("scoreboard.bar"));
							helper.add(left(result, 32));
						}
					}
					helper.update(online);
				}
			}

		}.runTaskTimer(this.plugin, 2, 2);
	}

	public String replaceAll(Player player, String input) {
		String result = input;
		if (result.contains("%player%")) {
			result = result.replace("%player%", player.getName());
		}
		if (result.contains("%playeronline%")) {
			result = result.replace("%playeronline%", String.valueOf(HubPlugin.ALL_PLAYER));
		}
		if (result.contains("%queue_space%")) {
			if (EzQueueUtils.getPosition(player) != -1) {
				result = result.replace("%queue_space%", "");
			} else {
				return null;
			}
		}
		if (result.contains("%queue_name%")) {
			if (EzQueueUtils.getPosition(player) != -1) {
				result = result.replace("%queue_name%", EzQueueUtils.getQueue(player));
			} else {
				return null;
			}
		}
		if (result.contains("%queue_position%")) {
			if (EzQueueUtils.getPosition(player) != -1) {
				result = result.replace("%queue_position%", String.valueOf(EzQueueUtils.getPosition(player)));
			} else {
				return null;
			}
		}
		if (result.contains("%queue_maxpos%")) {
			if (EzQueueUtils.getPosition(player) != -1) {
				result = result.replace("%queue_maxpos%",
						String.valueOf(EzQueueUtils.getQueueSize(EzQueueUtils.getQueue(player))));
			} else {
				return null;
			}
		}
		return result;
	}

	public boolean isOnlySpacer(List<String> texts) {
		boolean isOnlySpacer = true;
		for (String result : texts) {
			if (!result.contains("%spacer%") && !result.contains("%bar%")) {
				isOnlySpacer = false;
			}
		}
		return isOnlySpacer;
	}

	public String left(String input, int len) {
		if (input.length() <= len) {
			return input;
		}
		return input.substring(0, len);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.helpers.remove(event.getPlayer());
	}

	@EventHandler
	public void onPlayerdie(PlayerDeathEvent e) {
		Player player = ((OfflinePlayer) e).getPlayer();
		this.helpers.remove(player);
	}

}
