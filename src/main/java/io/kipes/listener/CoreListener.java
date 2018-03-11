package io.kipes.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import net.md_5.bungee.api.ChatColor;

public class CoreListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		event.getPlayer().sendMessage(ChatColor.RED + "You cannot build here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		event.getPlayer().sendMessage(ChatColor.RED + "You cannot build here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		event.getPlayer().sendMessage(ChatColor.RED + "You cannot drop items here.");
		event.setCancelled(true);
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onCreeper(EntityExplodeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onWeather(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}

	@EventHandler
	public void onHitPlayer(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player attacker = (Player) event.getDamager();
			Player attacked = (Player) event.getEntity();
			attacker.hidePlayer(attacked);
			attacker.sendMessage(ChatColor.GREEN + "Hiding " + attacked.getDisplayName());
			attacker.playSound(attacked.getLocation(), Sound.CHICKEN_EGG_POP, 2, 2);
		}
	}

}
