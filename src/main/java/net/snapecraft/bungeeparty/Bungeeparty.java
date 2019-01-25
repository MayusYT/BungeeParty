package net.snapecraft.bungeeparty;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.snapecraft.commands.PartyCMD;
import net.snapecraft.listener.ServerSwitchListener;

public final class Bungeeparty extends Plugin {

    public static Bungeeparty plugin;

    @Override
    public void onEnable() {
        init();
        System.out.println("\n\n\n _                                                  _         \n" +
                "| |                                                | |        \n" +
                "| |__  _   _ _ __   __ _  ___  ___ _ __   __ _ _ __| |_ _   _ \n" +
                "| '_ \\| | | | '_ \\ / _` |/ _ \\/ _ \\ '_ \\ / _` | '__| __| | | |\n" +
                "| |_) | |_| | | | | (_| |  __/  __/ |_) | (_| | |  | |_| |_| |\n" +
                "|_.__/ \\__,_|_| |_|\\__, |\\___|\\___| .__/ \\__,_|_|   \\__|\\__, |" + getDescription().getVersion() + "\n" +
                "                    __/ |         | |                    __/ |\n" +
                "                   |___/          |_|                   |___/ \n\n\n");
    }


    private void init() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PartyCMD());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitchListener());
    }

    @Override
    public void onDisable() {

    }

    public static Bungeeparty getPlugin() {
        return plugin;
    }
}
