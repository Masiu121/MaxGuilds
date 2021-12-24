package com.Oxology.MaxGuilds;

import com.Oxology.MaxGuilds.items.StonerItem;
import com.Oxology.MaxGuilds.listeners.StonerListener;
import com.Oxology.MaxGuilds.utils.ArrayUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class MaxGuilds extends JavaPlugin {
    StonerItem stonerItem;
    ArrayUtils arrayUtils;

    @Override
    public void onEnable() {
        stonerItem = new StonerItem(this);
        arrayUtils = new ArrayUtils();
        getServer().getPluginManager().registerEvents(new StonerListener(stonerItem, this, arrayUtils), this);
    }

    @Override
    public void onDisable() {

    }
}
