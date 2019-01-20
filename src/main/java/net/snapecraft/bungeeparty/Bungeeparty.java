package net.snapecraft.bungeeparty;

import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

public final class Bungeeparty extends Plugin {

    public static Bungeeparty plugin;

    @Override
    public void onEnable() {
        System.out.println("\n\n\n _                                                  _         \n" +
                "| |                                                | |        \n" +
                "| |__  _   _ _ __   __ _  ___  ___ _ __   __ _ _ __| |_ _   _ \n" +
                "| '_ \\| | | | '_ \\ / _` |/ _ \\/ _ \\ '_ \\ / _` | '__| __| | | |\n" +
                "| |_) | |_| | | | | (_| |  __/  __/ |_) | (_| | |  | |_| |_| |\n" +
                "|_.__/ \\__,_|_| |_|\\__, |\\___|\\___| .__/ \\__,_|_|   \\__|\\__, |" + getDescription().getVersion() + "\n" +
                "                    __/ |         | |                    __/ |\n" +
                "                   |___/          |_|                   |___/ \n\n\n");
    }

    @Override
    public void onDisable() {

    }

    public static Bungeeparty getPlugin() {
        return plugin;
    }
}
