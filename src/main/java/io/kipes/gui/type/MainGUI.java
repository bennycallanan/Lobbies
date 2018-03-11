package io.kipes.gui.type;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import io.kipes.HubPlugin;
import io.kipes.gui.Gui;
import io.kipes.util.ItemBuilderUtils;
import net.md_5.bungee.api.ChatColor;

public class MainGUI extends Gui implements Listener {

	private Inventory inv = Bukkit.createInventory(null, 9, this.getDisplayName());
	private ItemBuilderUtils cbkBuilder = new ItemBuilderUtils(Material.GOLD_HELMET).setName(ChatColor.AQUA + "CBK");
	private ItemBuilderUtils factionsBuilder = new ItemBuilderUtils(Material.DIAMOND_PICKAXE).setName(ChatColor.AQUA + "Factions");
	private ItemBuilderUtils squadsBuilder = new ItemBuilderUtils(Material.DIAMOND_SWORD).setName(ChatColor.AQUA + "Squads");
	private ItemBuilderUtils practiceBuilder = new ItemBuilderUtils(Material.BLAZE_POWDER).setName(ChatColor.AQUA + "Practice");
	private ItemBuilderUtils eventsBuilder = new ItemBuilderUtils(Material.CHEST).setName(ChatColor.AQUA + "Events");

	@Override
	public void getOpenGUI(Player player) {
		new BukkitRunnable() {

			@Override
			public void run() {

				ItemStack cbk = cbkBuilder.setLore(ChatColor.BLUE + "Players: " + HubPlugin.CBK + " / 500").toItemStack();
				ItemStack factions = factionsBuilder.setLore(ChatColor.BLUE + "Players: " + HubPlugin.FACTIONS + " / 500").toItemStack();
				ItemStack squads = squadsBuilder.setLore(ChatColor.BLUE + "Players: " + HubPlugin.SQUADS + " / 500").toItemStack();
				ItemStack practice = practiceBuilder.setLore(ChatColor.BLUE + "Players: " + HubPlugin.PRACTICE + " / 500").toItemStack();
				ItemStack events = eventsBuilder.setLore(ChatColor.BLUE + "Players: " + HubPlugin.EVENTS + " / 500").toItemStack();

				inv.setItem(0, cbk);
				inv.setItem(1, factions);
				inv.setItem(2, squads);
				inv.setItem(3, practice);
				inv.setItem(4, events);

			}

		}.runTaskTimerAsynchronously(HubPlugin.getPlugin(), 2, 2);

		player.openInventory(inv);

	}

	@Override
	public String getDisplayName() {
		return ChatColor.AQUA + "Server Selector";
	}

	@Override
	public Material getItem() {
		return Material.WATCH;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if (player.getItemInHand().getType() == this.getItem()) {
				this.getOpenGUI(player);
			}
		}
	}
	
	
	
	@EventHandler
	public void onGUIClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getTitle().equals(getDisplayName())) {
			if(event.getRawSlot() > 53) { event.setCancelled(true); }
		    if (event.getCurrentItem().getType() == cbkBuilder.toItemStack().getType()) {
				Bukkit.dispatchCommand(player, "play cbk"); //queue server
		    }    else if (event.getCurrentItem().getType() == factionsBuilder.toItemStack().getType()) {
				Bukkit.dispatchCommand(player, "play factions"); //queue server
			} else if (event.getCurrentItem().getType() == squadsBuilder.toItemStack().getType()) {
				Bukkit.dispatchCommand(player, "play squads"); //queue server
			} else if (event.getCurrentItem().getType() == practiceBuilder.toItemStack().getType()) {
				HubPlugin.getPlugin().sendPlayer(player, "practice");
			} else if (event.getCurrentItem().getType() == eventsBuilder.toItemStack().getType()) {
				Bukkit.dispatchCommand(player, "play events"); //queue server
			}
			event.setCancelled(true);
			return;
			}
		}
	
}
