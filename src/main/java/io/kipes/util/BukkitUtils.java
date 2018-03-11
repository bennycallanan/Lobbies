package io.kipes.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.google.common.base.Preconditions;

public class BukkitUtils {

	public static Location getHighestLocation(Location origin) {
		return getHighestLocation(origin, null);
	}

	public static Location getHighestLocation(Location origin, Location def) {
		Preconditions.checkNotNull((Object) origin, "The location cannot be null");
		Location cloned = origin.clone();
		World world = cloned.getWorld();
		int x = cloned.getBlockX();
		int y = world.getMaxHeight();
		int z = cloned.getBlockZ();
		while (y > origin.getBlockY()) {
			Block block = world.getBlockAt(x, --y, z);
			if (!block.isEmpty()) {
				Location next = block.getLocation();
				next.setPitch(origin.getPitch());
				next.setYaw(origin.getYaw());
				return next;
			}
		}
		return def;
	}
}
