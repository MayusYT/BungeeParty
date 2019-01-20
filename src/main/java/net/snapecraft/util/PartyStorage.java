package net.snapecraft.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.snapecraft.bungeeparty.Bungeeparty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PartyStorage {

    public static HashMap<ProxiedPlayer, List<ProxiedPlayer>> partyStorage = new HashMap<>();

    // Leader, toInvite
    public static HashMap<ProxiedPlayer, ProxiedPlayer> partyInvites = new HashMap<>();

    public static void addParty (ProxiedPlayer leader) {
        if(!partyStorage.containsKey(leader)) {
            partyStorage.put(leader, null);
        }
    }

    public static void removeParty (ProxiedPlayer leader) {
        partyStorage.remove(leader);
    }

    public static List<ProxiedPlayer> getParty (ProxiedPlayer leader) {
        return partyStorage.get(leader);
    }

    public static void addPlayerToParty (ProxiedPlayer leader, ProxiedPlayer toAdd) {
        List<ProxiedPlayer> memberList = partyStorage.get(leader);
        memberList.add(toAdd);
        partyStorage.replace(leader,  memberList);
    }

    public static void removePlayerFromParty (ProxiedPlayer leader, ProxiedPlayer toRemove) {
        List<ProxiedPlayer> memberList = partyStorage.get(leader);
        memberList.remove(toRemove);
        partyStorage.replace(leader,  memberList);
    }

    public static Boolean isPlayerInParty (ProxiedPlayer toQuery) {
        if(partyStorage.containsKey(toQuery)) {
            return true;
        }
        if(partyStorage.containsValue(toQuery)) {
            return true;
        }
        return false;
    }

    public static Boolean doesPlayerOwnParty (ProxiedPlayer toQuery) {
        return partyStorage.containsKey(toQuery);
    }

    public static ProxiedPlayer getLeaderOfPartyPlayerIsIn (ProxiedPlayer toQuery) {
        for(ProxiedPlayer pp : partyStorage.keySet()) {
            if(partyStorage.get(pp).contains(toQuery)) {
                return pp;
            }
        }
        return null;
    }

    public static void sendInvite (ProxiedPlayer leader, ProxiedPlayer toInvite) {
        partyInvites.put(leader, toInvite);
        toInvite.sendMessage(leader.getName() + " lädt dich zu seiner Party ein!\n/party accept\n/party decline");
        setInviteDecayTimer(leader, toInvite);
    }


    public static void setInviteDecayTimer (ProxiedPlayer leader, ProxiedPlayer toInvite) {
        ProxyServer.getInstance().getScheduler().schedule(Bungeeparty.getPlugin(), () -> {
            partyInvites.remove(leader, toInvite);
            leader.sendMessage(toInvite.getName() + " hat deine Anfrage nicht angenommen!");
            //TODO: cancel
        }, 1, 5, TimeUnit.MINUTES);
    }


    //TODO accept and decline invite
    public static void acceptInvite (ProxiedPlayer invitedPlayer, ProxiedPlayer leader) {

    }
}
