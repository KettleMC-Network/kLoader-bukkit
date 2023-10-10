package net.kettlemc.kloader.loading;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import com.alessiodp.libby.LibraryManager;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class DependencyLoader {

    public static void loadJaskl(Plugin plugin) {
        LibraryManager manager = new BukkitLibraryManager(plugin);
        manager.addMavenCentral();
        manager.loadLibrary(
                Library.builder()
                        .artifactId("jaskl-hocon")
                        .groupId("io{}github{}almighty-satan{}jaskl")
                        .version("1.4.1")
                        .resolveTransitiveDependencies(true)
                        .build()
        );
        manager.loadLibraries();
    }

    public static void load(Plugin plugin) {
        LibraryManager manager = new BukkitLibraryManager(plugin);
        manager.addMavenCentral();
        manager.addJCenter();
        manager.addSonatype();

        LoadingConfiguration.REPOSITORIES.getValue().forEach(manager::addRepository);
        LoadingConfiguration.DEPENDENCIES.getValue().stream().map(DependencyLoader::buildWithVersion).filter(Objects::nonNull).forEach(manager::loadLibrary);

        manager.loadLibraries();
    }

    private static Library buildWithVersion(String toParse) {
        String[] parts = toParse.replace(".", "{}").split(":");
        try {
            Library.Builder builder = Library.builder()
                    .groupId(parts[0])
                    .artifactId(parts[1])
                    .version(parts[2])
                    .resolveTransitiveDependencies(true);
            LoadingConfiguration.EXCLUDE.getValue().forEach(exclude -> {
                String[] excludeParts = split(exclude);
                builder.excludeTransitiveDependency(excludeParts[0], excludeParts[1]);
            });
            return builder.build();
        } catch (Exception e) {
            System.out.println("Failed to load dependency: " + toParse);
            return null;
        }
    }

    private static String[] split(String toSplit) {
        return toSplit.split(":");
    }
}
