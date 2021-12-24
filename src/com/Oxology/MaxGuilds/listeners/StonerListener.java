package com.Oxology.MaxGuilds.listeners;

import com.Oxology.MaxGuilds.MaxGuilds;
import com.Oxology.MaxGuilds.items.StonerItem;
import com.Oxology.MaxGuilds.utils.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class StonerListener implements Listener {
    MaxGuilds plugin;
    StonerItem item;
    NamespacedKey stonersKey;
    ArrayUtils arrayUtils;

    public StonerListener(StonerItem item, MaxGuilds plugin, ArrayUtils arrayUtils) {
        this.plugin = plugin;
        this.item = item;
        this.arrayUtils = arrayUtils;
        stonersKey = new NamespacedKey(this.plugin, "Stoners");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!event.getItemInHand().hasItemMeta()) return;
        if(!event.getItemInHand().getItemMeta().hasLore()) return;
        if(event.getItemInHand().getType() != Material.REDSTONE_BLOCK) return;
        if(!event.getItemInHand().getItemMeta().toString().equals(item.getItemMeta().toString())) return;

        event.getBlock().setType(Material.STONE);

        Block block = event.getBlock();
        PersistentDataContainer container = block.getChunk().getPersistentDataContainer();

        List<Location> stoners;

        String encodedStoners = container.get(stonersKey, PersistentDataType.STRING);
        if(encodedStoners == null || encodedStoners.equals(" ") || encodedStoners.equals(""))
            stoners = new ArrayList<>();
        else
            stoners = arrayUtils.decodeString(encodedStoners);

        stoners.add(block.getLocation());
        container.set(stonersKey, PersistentDataType.STRING, arrayUtils.encodeString(stoners));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() != Material.STONE) return;

        Block block = event.getBlock();
        PersistentDataContainer container = block.getChunk().getPersistentDataContainer();

        String encodedStoners = container.get(stonersKey, PersistentDataType.STRING);
        if(encodedStoners == null || encodedStoners.equals(" ") || encodedStoners.equals("")) return;

        List<Location> stoners = arrayUtils.decodeString(encodedStoners);
        if(getStoner(stoners, block.getLocation()) == null) return;

        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE) {
            stoners.remove(getStoner(stoners, block.getLocation()));
            container.set(stonersKey, PersistentDataType.STRING, arrayUtils.encodeString(stoners));
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), item);
        }
        else {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    event.getBlock().setType(Material.STONE);
                }
            }, 20L);
        }
    }

    public Location getStoner(List<Location> stoners, Location blockLocation) {
        if(stoners.size() < 1) return null;

        for(Location stonerLocation : stoners) {
            if( stonerLocation.getWorld() == blockLocation.getWorld() &&
                stonerLocation.getBlockX() == blockLocation.getBlockX() &&
                stonerLocation.getBlockY() == blockLocation.getBlockY() &&
                stonerLocation.getBlockZ() == blockLocation.getBlockZ()
            ) return stonerLocation;
        }

        return null;
    }
}
