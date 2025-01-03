package net.kettlemc.kloader;

import net.kettlemc.kloader.loading.DependencyLoader;
import net.kettlemc.kloader.loading.Loadable;
import net.kettlemc.kloader.loading.LoadingConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of the plugin.
 * Loads libraries from the dependencies.json file.
 */
public final class KLoader implements Loadable {

    private static KLoader instance;

    private final JavaPlugin plugin;

    public KLoader(JavaPlugin plugin) {
        instance = this;
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
        DependencyLoader.loadJaskl(plugin);

        if (!LoadingConfiguration.load()) {
            this.plugin.getLogger().severe("Failed to load configuration!");
            return;
        }

        DependencyLoader.load(plugin);
    }

    public static KLoader instance() {
        return instance;
    }

    @Override
    public void onDisable() {
        LoadingConfiguration.unload();
    }

    public JavaPlugin plugin() {
        return this.plugin;
    }
}
