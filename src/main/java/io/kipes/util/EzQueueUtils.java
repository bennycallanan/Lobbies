package io.kipes.util;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.signatured.ezqueueshared.QueueInfo;
import me.signatured.ezqueuespigot.util.EzQueueUtil;

public class EzQueueUtils {

	/**
	 * @param name
	 *            Name of the player you want the position of
	 * @return Position of player requested. Returns -1 if they aren't queued.
	 */
	public static int getPosition(String name) {
		Player player = Bukkit.getPlayer(name);

		if (player == null)
			return -1;

		return QueueInfo.getPosition(player.getName());
	}

	/**
	 * @param uuid
	 *            UUID of the player you want the position of
	 * @return Position of player requested. Returns -1 if they aren't queued.
	 */
	public static int getPosition(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);

		if (player == null)
			return -1;

		return QueueInfo.getPosition(player.getName());
	}

	/**
	 * @param player
	 *            Player of the player you want the position of
	 * @return Position of player requested. Returns -1 if they aren't queued.
	 */
	public static int getPosition(Player player) {
		return getPosition(player.getName());
	}

	/**
	 * @param queue
	 *            Queue you wish to get the players in
	 * @return Map of players with their current positions
	 */
	public static Map<String, Integer> getPlayersInQueue(String queue) {
		QueueInfo info = QueueInfo.getQueueInfo(queue);
		if (info == null)
			return null;

		return info.getPlayersInQueue();
	}

	/**
	 * @param name
	 *            Name of player you wish to get the queue for
	 * @return Queue player is currently in. Null if not queueud
	 */
	public static String getQueue(String name) {
		return QueueInfo.getQueue(name);
	}

	/**
	 * @param uuid
	 *            UUID of player you wish to get the queue for
	 * @return Queue player is currently in. Null if not queueud
	 */
	public static String getQueue(UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		if (player == null)
			return null;

		return QueueInfo.getQueue(player.getName());
	}

	/**
	 * @param player
	 *            Player of player you wish to get the queue for
	 * @return Queue player is currently in. Null if not queueud
	 */
	public static String getQueue(Player player) {
		return getQueue(player.getName());
	}

	/**
	 * @param queue
	 *            Name of queue you wish to get the size of
	 * @return Size of queue requested. Returns -1 if queue doesn't exist
	 */
	public static int getQueueSize(String queue) {
		QueueInfo info = QueueInfo.getQueueInfo(queue);

		if (info == null)
			return -1;

		return info.getSize();
	}

	/**
	 * @param player
	 *            Player to add to the queue
	 * @param queue
	 *            Queue to add the given player to
	 */
	public static void addToQueue(Player player, String queue) {
		EzQueueUtil.sendJoinRequest(player, queue);
	}

}
