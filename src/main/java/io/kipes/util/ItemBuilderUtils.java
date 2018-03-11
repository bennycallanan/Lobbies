package io.kipes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilderUtils {

	private ItemStack is;

	public ItemBuilderUtils(Material m) {
		this(m, 1);
	}

	public ItemBuilderUtils(ItemStack is) {
		this.is = is;
	}

	public ItemBuilderUtils(Material m, int amount) {
		is = new ItemStack(m, amount);
	}

	public ItemBuilderUtils(Material m, int amount, byte durability) {
		is = new ItemStack(m, amount, durability);
	}

	@Override
	public ItemBuilderUtils clone() {
		return new ItemBuilderUtils(is);
	}

	public ItemBuilderUtils setDurability(short dur) {
		is.setDurability(dur);
		return this;
	}

	public ItemBuilderUtils setName(String name) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils addUnsafeEnchantment(Enchantment ench, int level) {
		is.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemBuilderUtils removeEnchantment(Enchantment ench) {
		is.removeEnchantment(ench);
		return this;
	}

	public ItemBuilderUtils setSkullOwner(String owner) {
		try {
			SkullMeta im = (SkullMeta) is.getItemMeta();
			im.setOwner(owner);
			is.setItemMeta(im);
		} catch (ClassCastException expected) {
		}
		return this;
	}

	public ItemBuilderUtils addEnchant(Enchantment ench, int level) {
		ItemMeta im = is.getItemMeta();
		im.addEnchant(ench, level, true);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils addEnchantments(Map<Enchantment, Integer> enchantments) {
		is.addEnchantments(enchantments);
		return this;
	}

	public ItemBuilderUtils setInfinityDurability() {
		is.setDurability(Short.MAX_VALUE);
		return this;
	}

	public ItemBuilderUtils setLore(String... lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils setLore(List<String> lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils removeLoreLine(String line) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		if (!lore.contains(line))
			return this;
		lore.remove(line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils removeLoreLine(int index) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		if (index < 0 || index > lore.size())
			return this;
		lore.remove(index);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils addLoreLine(String line) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>();
		if (im.hasLore())
			lore = new ArrayList<>(im.getLore());
		lore.add(line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	public ItemBuilderUtils addLoreLine(String line, int pos) {
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		lore.set(pos, line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}

	@SuppressWarnings("deprecation")
	public ItemBuilderUtils setDyeColor(DyeColor color) {
		this.is.setDurability(color.getData());
		return this;
	}

	@Deprecated
	public ItemBuilderUtils setWoolColor(DyeColor color) {
		if (!is.getType().equals(Material.WOOL))
			return this;
		this.is.setDurability(color.getData());
		return this;
	}

	public ItemBuilderUtils setLeatherArmorColor(Color color) {
		try {
			LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
			im.setColor(color);
			is.setItemMeta(im);
		} catch (ClassCastException expected) {
		}
		return this;
	}

	public ItemStack toItemStack() {
		return is;
	}
}