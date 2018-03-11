package io.kipes.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Gui {

	public abstract void getOpenGUI(Player player);

	public abstract String getDisplayName();

	public abstract Material getItem();
}
