package io.kipes.gui;

import io.kipes.gui.type.MainGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import io.kipes.HubPlugin;
import lombok.Getter;

public class GuiManager {

	@Getter
	private MainGUI mainGUI;

	public GuiManager(HubPlugin plugin) {
		this.registerGUI(this.mainGUI = new MainGUI());
	}

	private void registerGUI(Gui gui) {
		if (gui instanceof Listener) {
			Bukkit.getPluginManager().registerEvents((Listener) gui, HubPlugin.getPlugin());
		}
	}

}
