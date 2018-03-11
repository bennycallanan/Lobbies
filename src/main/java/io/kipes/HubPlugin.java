package io.kipes;

import io.kipes.command.HubCommand;
import io.kipes.gui.GuiManager;
import io.kipes.listener.ChatListener;
import io.kipes.listener.CoreListener;
import io.kipes.listener.PlayerListener;
import io.kipes.scoreboard.ScoreboardManager;
import io.kipes.scoreboard.providor.ScoreboardProvidor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class HubPlugin extends JavaPlugin implements PluginMessageListener, Listener {

	@Getter
	private static HubPlugin plugin;
	@Getter
	private GuiManager guiManager;
	@Getter
	private static HubPlugin instance;
	@Getter
	private ScoreboardManager scoreboardManager;
	@Getter
	private ScoreboardProvidor scoreboardProvidor;

	@Override
	public void onEnable() {
		instance = this;
		plugin = this;

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

		loadConfig();
		registerCommands();
		registerListeners();
		registerManagers();

		getScoreboardProvidor().init();
		getScoreboardManager().init();

		getCounCollector();
	}

	private void registerCommands() {
		getCommand("hubplugin").setExecutor(new HubCommand(this));
	}

	private void registerListeners() {
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new CoreListener(), this);
		manager.registerEvents(new PlayerListener(this), this);
		manager.registerEvents(new ScoreboardManager(this), this);
		manager.registerEvents(new ScoreboardProvidor(this), this);
		manager.registerEvents(new ChatListener(this), this);
	}

	private void registerManagers() {
		guiManager = new GuiManager(this);
		scoreboardManager = new ScoreboardManager(this);
		scoreboardProvidor = new ScoreboardProvidor(this);
	}

	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	public void sendPlayer(Player player, String s) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(s);
		player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
		player.sendMessage(ChatColor.GREEN + "Attempting to connect to " + s + ".....");
	}

	public void setNewSpawnLocation(Player player) {
		getConfig().set("Spawn.X", player.getLocation().getX());
		getConfig().set("Spawn.Y", player.getLocation().getY());
		getConfig().set("Spawn.Z", player.getLocation().getZ());
		getConfig().set("Spawn.YAW", player.getLocation().getYaw());
		getConfig().set("Spawn.PITCH", player.getLocation().getPitch());
		getConfig().set("Spawn.WORLD", player.getLocation().getWorld().getName());
		saveConfig();
		player.sendMessage(ChatColor.RED + "Spawn location has been updated.");
	}

	public Location getSpawnLocation() {
		double x = getConfig().getDouble("Spawn.X");
		double y = getConfig().getDouble("Spawn.Y");
		double z = getConfig().getDouble("Spawn.Z");
		String w = getConfig().getString("Spawn.WORLD");
		Location l = new Location(Bukkit.getWorld(w), x, y, z);
		return l;
	}

	public static int ALL_PLAYER = 0;
	public static int CBK = 0;
	public static int FACTIONS = 0;
	public static int SQUADS = 0;
	public static int EVENTS = 0;
	public static int PRACTICE = 0;

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		try {
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
			String command = in.readUTF();

			if (command.equals("PlayerCount")) {
				String subchannel = in.readUTF();
				if (subchannel.equals("ALL")) {
					int playercount = in.readInt();
					ALL_PLAYER = playercount;
				}
				if (subchannel.equalsIgnoreCase("cbk")) {
					int playercount = in.readInt();
					CBK = playercount;
				}
				if (subchannel.equalsIgnoreCase("factions")) {
					int playercount = in.readInt();
					FACTIONS = playercount;
				}
				if (subchannel.equalsIgnoreCase("squads")) {
					int playercount = in.readInt();
					SQUADS = playercount;
				}
				if (subchannel.equalsIgnoreCase("events")) {
					int playercount = in.readInt();
					EVENTS = playercount;
				}
				if (subchannel.equalsIgnoreCase("practice")) {
					int playercount = in.readInt();
					PRACTICE = playercount;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getCount(String server) {
		try {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("PlayerCount");
			out.writeUTF(server);

			Bukkit.getServer().sendPluginMessage(HubPlugin.getPlugin(), "BungeeCord", out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getServerCountList() {
		getCount("ALL");
		getCount("cbk");
		getCount("factions");
		getCount("squads");
		getCount("events");
		getCount("practice");
	}

	private void getCounCollector() {
		new BukkitRunnable() {

			@Override
			public void run() {
				HubPlugin.getServerCountList();
			}

		}.runTaskTimer(plugin, 1, 1);
	}
}