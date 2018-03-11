package io.kipes.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import io.kipes.HubPlugin;

public class ScoreboardManager implements Listener {

	private HubPlugin plugin;
	private Map<Player, ScoreboardHelper> helpers = new HashMap<Player, ScoreboardHelper>();

	public ScoreboardManager(HubPlugin plugin) {
		this.plugin = plugin;
	}

	public void init() {
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
		for (Player online : Bukkit.getOnlinePlayers()) {
			this.handleJoin(online);
		}
	}

	public ScoreboardHelper getScoreboardFor(Player player) {
		return this.helpers.get(player);
	}

	public void handleJoin(Player player) {
		Scoreboard bukkitScoreBoard = this.plugin.getServer().getScoreboardManager().getNewScoreboard();
		player.setScoreboard(bukkitScoreBoard);
		ScoreboardHelper helper = new ScoreboardHelper(bukkitScoreBoard,
				this.plugin.getConfig().getString("scoreboard.title"));
		this.helpers.put(player, helper);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		handleJoin(event.getPlayer());
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		this.helpers.remove(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.helpers.remove(event.getPlayer());
	}
}
