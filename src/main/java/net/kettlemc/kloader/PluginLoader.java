package net.kettlemc.kloader;

import net.kettlemc.kloader.loading.Loadable;

public class PluginLoader extends org.bukkit.plugin.java.JavaPlugin {

    Loadable plugin;

    @Override
    public void onLoad() {
        this.plugin = new KLoader(this);
        plugin.onLoad();
    }

    @Override
    public void onEnable() {
        plugin.onEnable();
    }

    @Override
    public void onDisable() {
        plugin.onDisable();
    }


}
