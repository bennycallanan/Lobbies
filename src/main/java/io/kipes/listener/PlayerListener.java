package io.kipes.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.kipes.util.BukkitUtils;
import io.kipes.util.ItemBuilderUtils;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.kipes.HubPlugin;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {

	private ItemBuilderUtils compassItem = new ItemBuilderUtils(Material.WATCH);
	private ItemBuilderUtils enderpearlItem = new ItemBuilderUtils(Material.ENDER_PEARL, 16);
	private ItemBuilderUtils welcomeItem = new ItemBuilderUtils(Material.BOOK);
	private HashMap<UUID, Integer> doubleJump = new HashMap<UUID, Integer>();
	private HubPlugin plugin;

	public PlayerListener(HubPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPlayedBefore()) {
			this.setPlayer(player);
		} else {
			this.setPlayer(player);
		}
	}

	@EventHandler
	public void setFlyOnJump(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		Vector jump = player.getLocation().getDirection().multiply(0.5).setY(1.25);
		if (event.isFlying() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (this.doubleJump.containsKey(player.getUniqueId())) {
				double getJump = this.doubleJump.get(player.getUniqueId());
				double newJump = getJump + 1;
				if (this.doubleJump.get(player.getUniqueId()).intValue() == 2) {
					new BukkitRunnable() {

						@Override
						public void run() {
							PlayerListener.this.doubleJump.remove(player.getUniqueId());
							player.sendMessage("You can now double jump again.");
						}

					}.runTaskLater(plugin, TimeUnit.SECONDS.toMillis(5) / 50);
					return;
				}

			} else {
				this.doubleJump.put(player.getUniqueId(), 1);
			}
			player.setFlying(false);
			player.setVelocity(player.getVelocity().add(jump));
			event.setCancelled(true);
		}
	}

	private void giveItem(Player player) {
		player.getInventory().clear();

		ArrayList<String> cbklore = new ArrayList<String>();

		this.compassItem.setName(ChatColor.GREEN + "Server Selector");
		this.enderpearlItem.setName(ChatColor.RED + "Ender Butts");
        this.welcomeItem.setName(ChatColor.GOLD + "Welcome");
		
		player.getInventory().setItem(0, compassItem.toItemStack());
		player.getInventory().setItem(1, welcomeItem.toItemStack());
		player.getInventory().setItem(2, enderpearlItem.toItemStack());
	}

	public static void giveArmor(Player player) {
		if (player.hasPermission("perm.basic")) {
			ItemStack lChest = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta chest = (LeatherArmorMeta) lChest.getItemMeta();
			chest.setDisplayName(ChatColor.GREEN + "Basic Gadgets");
			chest.setColor(Color.BLUE);
			lChest.setItemMeta(chest);
			lChest.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack lLeggins = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta legs = (LeatherArmorMeta) lLeggins.getItemMeta();
			legs.setDisplayName(ChatColor.GREEN + "Basic Gadgets");
			legs.setColor(Color.BLUE);
			lLeggins.setItemMeta(legs);
			lLeggins.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack lBoots = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta boots = (LeatherArmorMeta) lBoots.getItemMeta();
			boots.setDisplayName(ChatColor.GREEN + "Basic Gadgets");
			boots.setColor(Color.BLUE);
			lBoots.setItemMeta(boots);
			lBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			player.getInventory().setChestplate(lChest);
			player.getInventory().setLeggings(lLeggins);
			player.getInventory().setBoots(lBoots);
		} else if (player.hasPermission("perm.silver")) {
			ItemStack sChestplate = new ItemStack(Material.IRON_CHESTPLATE);
			sChestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack sLeggins = new ItemStack(Material.IRON_LEGGINGS);
			sLeggins.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack sBoots = new ItemStack(Material.IRON_BOOTS);
			sBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			player.getInventory().setChestplate(sChestplate);
			player.getInventory().setLeggings(sLeggins);
			player.getInventory().setBoots(sBoots);
		} else if (player.hasPermission("perm.gold")) {
			ItemStack gChestplate = new ItemStack(Material.GOLD_CHESTPLATE);
			gChestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack gLeggins = new ItemStack(Material.GOLD_LEGGINGS);
			gLeggins.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack gBoots = new ItemStack(Material.GOLD_BOOTS);
			gBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			player.getInventory().setChestplate(gChestplate);
			player.getInventory().setLeggings(gLeggins);
			player.getInventory().setBoots(gBoots);
		} else if (player.hasPermission("perm.platinum")) {
			ItemStack dChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
			dChestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack dLeggins = new ItemStack(Material.DIAMOND_LEGGINGS);
			dLeggins.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack dBoots = new ItemStack(Material.DIAMOND_BOOTS);
			dBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			player.getInventory().setChestplate(dChestplate);
			player.getInventory().setLeggings(dLeggins);
			player.getInventory().setBoots(dBoots);
		} else if (player.hasPermission("perm.media")) {
			ItemStack mlChest = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta chest = (LeatherArmorMeta) mlChest.getItemMeta();
			chest.setColor(Color.BLACK);
			mlChest.setItemMeta(chest);
			mlChest.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack mlLeggins = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta legs = (LeatherArmorMeta) mlLeggins.getItemMeta();
			legs.setColor(Color.BLACK);
			mlLeggins.setItemMeta(legs);
			mlLeggins.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack mlBoots = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta boots = (LeatherArmorMeta) mlBoots.getItemMeta();
			boots.setColor(Color.BLUE);
			mlBoots.setItemMeta(boots);
			mlBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			player.getInventory().setChestplate(mlChest);
			player.getInventory().setLeggings(mlLeggins);
			player.getInventory().setBoots(mlBoots);
		} else if (player.hasPermission("perm.staff") || player.isOp()) {
			ItemStack cChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			cChestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack cLeggins = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			cLeggins.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			ItemStack cBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
			cBoots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

			player.getInventory().setChestplate(cChestplate);
			player.getInventory().setLeggings(cLeggins);
			player.getInventory().setBoots(cBoots);
		}
	}

	private void setPlayer(Player player) {
		this.giveItem(player);
		giveArmor(player);
		player.teleport(plugin.getSpawnLocation());
	}

	@EventHandler
	public void onIneract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if ((event.getAction().equals(Action.RIGHT_CLICK_AIR)) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
				|| (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
				|| (event.getAction().equals(Action.LEFT_CLICK_AIR))) {
			if (player.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
				event.setCancelled(true);
				event.setUseInteractedBlock(Result.DENY);
				event.setUseItemInHand(Result.DENY);
				player.setVelocity(player.getLocation().getDirection().normalize().multiply(2.5D));
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 2.0F, 2.0F);
				player.playEffect(EntityEffect.FIREWORK_EXPLODE);

			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
			Entity entity = event.getEntity();
			if (entity instanceof Player) {
				// Allow players to die by VOID in the END
				if (entity.getWorld().getEnvironment() == World.Environment.NETHER) {
					return;
				}

				Location destination = BukkitUtils.getHighestLocation(entity.getLocation());
				if (destination == null)
					return;

				if (entity.teleport(destination, PlayerTeleportEvent.TeleportCause.PLUGIN)) {
					event.setCancelled(true);
					((Player) entity).sendMessage(ChatColor.YELLOW + "You were saved from the void.");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();

		if (e.getPlayer().getLocation().getY() < 0.0D) {
			player.teleport(this.plugin.getSpawnLocation());
		}
	}
}