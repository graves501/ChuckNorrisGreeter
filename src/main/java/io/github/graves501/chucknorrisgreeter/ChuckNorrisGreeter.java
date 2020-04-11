package io.github.graves501.chucknorrisgreeter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChuckNorrisGreeter extends JavaPlugin {

    @Override
    public void onEnable() {
        // Don't log enabling, Spigot does that for you automatically!
        Bukkit.getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Don't log disabling, Spigot does that for you automatically!
    }
}
