package net.snapecraft.util;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class API {

    private static API instance = new API();

    public static API getInstance() {
        return instance;
    }


    public void sendHelp(ProxiedPlayer p) {
        p.sendMessage("-+-+-+-+-+ Party-Hilfe +-+-+-+-+-");
        p.sendMessage("/party invite <SPIELER>");
        p.sendMessage("/party accept <SPIELER>");
        p.sendMessage("/party decline <SPIELER>");
        p.sendMessage("-------");
        p.sendMessage("/party list");
        p.sendMessage("/party leave");
    }
}
