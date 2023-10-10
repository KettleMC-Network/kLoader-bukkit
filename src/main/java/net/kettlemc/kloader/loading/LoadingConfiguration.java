package net.kettlemc.kloader.loading;

import io.github.almightysatan.jaskl.Config;
import io.github.almightysatan.jaskl.Type;
import io.github.almightysatan.jaskl.entries.ListConfigEntry;
import io.github.almightysatan.jaskl.hocon.HoconConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class LoadingConfiguration {

    public static final Path CONFIG_DIRECTORY = Paths.get("plugins", "kLoader");
    private static final Config CONFIG = HoconConfig.of(CONFIG_DIRECTORY.resolve("config.conf").toFile(), "Config for example values");

    public static final ListConfigEntry<String> REPOSITORIES = ListConfigEntry.of(CONFIG, "repositories", "Repositories to check (mavenCentral, sonatype and jcenter are included by default)", Collections.emptyList(), Type.STRING);
    public static final ListConfigEntry<String> DEPENDENCIES = ListConfigEntry.of(CONFIG, "dependencies", "Dependencies to load in format GROUP:ID:VERSION", Collections.emptyList(), Type.STRING);
    public static final ListConfigEntry<String> EXCLUDE = ListConfigEntry.of(CONFIG, "exclude", "Dependencies to exclude from transitive dependencies in format GROUP:ID", Collections.emptyList(), Type.STRING);

    private LoadingConfiguration() {
    }

    public static boolean load() {
        try {
            CONFIG.load();
            CONFIG.write();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void unload() {
        CONFIG.close();
    }

    public static boolean write() {
        try {
            CONFIG.write();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
