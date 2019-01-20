package net.snapecraft.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.snapecraft.util.PartyStorage;

public class PartyCMD extends Command {

    public PartyCMD() {
        super("party");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) sender;

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("invite"))  {
                // Check, if the player  is in a party
                if(PartyStorage.isPlayerInParty(p)) {
                    // Check, if the player doesn't own the party
                    if(!PartyStorage.doesPlayerOwnParty(p)) {
                        p.sendMessage("§cNur Owner einer Party können Spieler einladen!");
                    } else {
                        if(ProxyServer.getInstance().getPlayer(args[1]) != null) {
                            PartyStorage.addPlayerToParty(p, ProxyServer.getInstance().getPlayer(args[1]));
                        } else {
                            p.sendMessage("§cDieser Spieler ist nicht online!");
                        }

                    }

                } else {
                    if(ProxyServer.getInstance().getPlayer(args[1]) != null) {
                        if(!PartyStorage.isPlayerInParty(ProxyServer.getInstance().getPlayer(args[1])) {
                            PartyStorage.addParty(p);
                            PartyStorage.addPlayerToParty(p, ProxyServer.getInstance().getPlayer(args[1]));
                        }

                    } else {
                        p.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                }
            }

        } else if (args.length == 1) {
            if(args[0].equalsIgnoreCase("list")) {
                if(PartyStorage.isPlayerInParty(p)) {
                    //TODO list cmd
                } else {

                }
                p.sendMessage(PartyStorage.getParty());
            }
        }

    }
}
