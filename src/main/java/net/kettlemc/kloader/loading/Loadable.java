package net.kettlemc.kloader.loading;

public interface Loadable {

    default void onLoad() {
    }

    default void onEnable() {
    }

    default void onDisable() {
    }
}
