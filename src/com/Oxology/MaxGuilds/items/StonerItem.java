package com.Oxology.MaxGuilds.items;

import com.Oxology.MaxGuilds.MaxGuilds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StonerItem extends ItemStack {
    MaxGuilds plugin;

    public StonerItem(MaxGuilds plugin) {
        super(Material.REDSTONE_BLOCK);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Stoner");
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Use golden pickaxe");
        lore.add(ChatColor.GRAY + "to get back stoner");

        meta.setLore(lore);
        this.setItemMeta(meta);

        this.plugin = plugin;
        setRecipe();
    }

    private void setRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "Stoner"), this);
        recipe.shape("EPE", "RDR", "EPE");
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('P', Material.PISTON);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(recipe);
    }
}
