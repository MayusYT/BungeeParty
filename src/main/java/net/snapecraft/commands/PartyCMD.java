package net.snapecraft.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.snapecraft.util.API;
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
                if(PartyStorage.getInstance().isPlayerInParty(p)) {
                    // Check, if the player doesn't own the party
                    if(!PartyStorage.getInstance().doesPlayerOwnParty(p)) {
                        p.sendMessage("§cNur Owner einer Party können Spieler einladen!");
                    } else {
                        if(ProxyServer.getInstance().getPlayer(args[1]) != null) {
                            //PartyStorage.addPlayerToParty(p, ProxyServer.getInstance().getPlayer(args[1]));
                            PartyStorage.getInstance().sendInvite(p, ProxyServer.getInstance().getPlayer(args[1]));
                        } else {
                            p.sendMessage("§cDieser Spieler ist nicht online!");
                        }

                    }

                } else {
                    if(ProxyServer.getInstance().getPlayer(args[1]) != null) {
                        if(!PartyStorage.getInstance().isPlayerInParty(ProxyServer.getInstance().getPlayer(args[1]))) {
                            PartyStorage.getInstance().addParty(p);
                            PartyStorage.getInstance().sendInvite(p, ProxyServer.getInstance().getPlayer(args[1]));
                        }

                    } else {
                        p.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                }
            } else if(args[0].equalsIgnoreCase("accept")) {
                if(!PartyStorage.getInstance().isPlayerInParty(p)) {
                    if(ProxyServer.getInstance().getPlayer(args[1]) != null) {
                        if(PartyStorage.getInstance().partyInvites.containsKey(ProxyServer.getInstance().getPlayer(args[1]))) {
                            if(PartyStorage.getInstance().partyInvites.get(ProxyServer.getInstance().getPlayer(args[1])) == p) {
                                PartyStorage.getInstance().acceptInvite(p, ProxyServer.getInstance().getPlayer(args[1]));
                            } else {
                                p.sendMessage("Du hast keine Einladung von diesem Spieler enthalten!");
                            }
                        } else {
                            p.sendMessage("Du hast keine Einladung von diesem Spieler enthalten!");
                        }
                    } else {
                        p.sendMessage("Dieser Spieler ist nicht online!");
                    }

                } else {
                    p.sendMessage("Du bist bereits in einer Party!");
                }
            } else if(args[0].equalsIgnoreCase("decline")) {
                if(!PartyStorage.getInstance().isPlayerInParty(p)) {
                    if(ProxyServer.getInstance().getPlayer(args[1]) != null) {
                        if(PartyStorage.getInstance().partyInvites.containsKey(ProxyServer.getInstance().getPlayer(args[1]))) {
                            if(PartyStorage.getInstance().partyInvites.get(ProxyServer.getInstance().getPlayer(args[1])) == p) {
                                PartyStorage.getInstance().declineInvite(p, ProxyServer.getInstance().getPlayer(args[1]));
                            } else {
                                p.sendMessage("Du hast keine Einladung von diesem Spieler enthalten!");
                            }
                        } else {
                            p.sendMessage("Du hast keine Einladung von diesem Spieler enthalten!");
                        }
                    } else {
                        p.sendMessage("Dieser Spieler ist nicht online!");
                    }

                } else {
                    p.sendMessage("Du bist bereits in einer Party!");
                }
            } else {
                API.getInstance().sendHelp(p);
            }

        } else if (args.length == 1) {
            if(args[0].equalsIgnoreCase("list")) {
                if(PartyStorage.getInstance().isPlayerInParty(p)) {
                    //TODO list cmd
                    if(PartyStorage.getInstance().doesPlayerOwnParty(p)) {
                        p.sendMessage(PartyStorage.getInstance().getParty(p).toString());
                    } else {
                        p.sendMessage(PartyStorage.getInstance().getParty(PartyStorage.getInstance().getLeaderOfPartyPlayerIsIn(p)).toString());
                    }

                } else {
                    p.sendMessage("Du bist in keiner Party");
                }

            } else if(args[0].equalsIgnoreCase("leave")) {
                if(!PartyStorage.getInstance().doesPlayerOwnParty(p)) {
                    PartyStorage.getInstance().removePlayerFromParty(PartyStorage.getInstance().getLeaderOfPartyPlayerIsIn(p), p);
                    p.sendMessage("Du hast die Party verlassen!");
                } else {
                    PartyStorage.getInstance().removeParty(p);
                    p.sendMessage("Die Party wurde aufgelöst!");
                }

            } else {
                API.getInstance().sendHelp(p);
            }
        } else {
            API.getInstance().sendHelp(p);
        }

    }
}
