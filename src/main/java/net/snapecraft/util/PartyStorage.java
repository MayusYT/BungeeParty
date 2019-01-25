package net.snapecraft.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.snapecraft.bungeeparty.Bungeeparty;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PartyStorage {

    private static PartyStorage instance = new PartyStorage();

    public static PartyStorage getInstance() {
        return instance;
    }

    public HashMap<ProxiedPlayer, List<ProxiedPlayer>> partyStorage = new HashMap<>();

    public HashMap<ProxiedPlayer, ProxiedPlayer> partyInvites = new HashMap<>();

    public void addParty (ProxiedPlayer leader) {
        if(!partyStorage.containsKey(leader)) {
            partyStorage.put(leader, null);
        }
    }

    public void removeParty (ProxiedPlayer leader) {
        for(ProxiedPlayer partyMember : getParty(leader)) {
            partyMember.sendMessage("Die Party wurde aufgelöst, da der Anführer diese verlassen hat!");
        }
        partyStorage.remove(leader);
    }

    public List<ProxiedPlayer> getParty (ProxiedPlayer leader) {
        return partyStorage.get(leader);
    }

    public void addPlayerToParty (ProxiedPlayer leader, ProxiedPlayer toAdd) {
        if(partyStorage.get(leader) != null) {
            List<ProxiedPlayer> memberList = partyStorage.get(leader);
            memberList.add(toAdd);
            partyStorage.replace(leader,  memberList);
            for(ProxiedPlayer partyMember : getParty(leader)) {
                partyMember.sendMessage("Der Spieler " + toAdd.getName() + " hat die Party betreten!");
            }
        } else {
            partyStorage.put(leader, new ArrayList<>(Collections.singletonList(toAdd)));
        }

    }

    public void removePlayerFromParty (ProxiedPlayer leader, ProxiedPlayer toRemove) {
        List<ProxiedPlayer> memberList = partyStorage.get(leader);
        memberList.remove(toRemove);
        partyStorage.replace(leader,  memberList);
        for(ProxiedPlayer partyMember : getParty(leader)) {
            partyMember.sendMessage("Der Spieler " + leader.getName() + " hat die Party verlassen!");
        }
    }

    /**
     * Even returns true if the queried player is the party leader!
     * @param toQuery Player that is to query.
     * @return returns true or false whether the player is in a party or not.
     */
    public Boolean isPlayerInParty (ProxiedPlayer toQuery) {
        if(partyStorage.containsKey(toQuery)) {
            return true;
        }

        //TODO Repair this: (doesn't work)
        for(ProxiedPlayer partyLeader : partyStorage.keySet()) {
            if(partyStorage.get(partyLeader).contains(toQuery)) {
                return true;
            }
        }
        return false;
    }

    public Boolean doesPlayerOwnParty (ProxiedPlayer toQuery) {
        return partyStorage.containsKey(toQuery);
    }

    public ProxiedPlayer getLeaderOfPartyPlayerIsIn (ProxiedPlayer toQuery) {
        for(ProxiedPlayer pp : partyStorage.keySet()) {
            if(partyStorage.get(pp).contains(toQuery)) {
                return pp;
            }
        }
        return null;
    }

    public void sendInvite (ProxiedPlayer leader, ProxiedPlayer toInvite) {
        partyInvites.put(leader, toInvite);
        toInvite.sendMessage(leader.getName() + " lädt dich zu seiner Party ein!\n/party accept\n/party decline");
        setInviteDecayTimer(leader, toInvite);
    }

    private ScheduledTask task;
    private void setInviteDecayTimer (ProxiedPlayer leader, ProxiedPlayer toInvite) {
        task = ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance().getPluginManager().getPlugin("Bungeeparty"), () -> {
            if(partyInvites.get(leader) == toInvite) {
                partyInvites.remove(leader, toInvite);
                leader.sendMessage(toInvite.getName() + " hat deine Anfrage nicht angenommen!");
                task.cancel();
            } else {
                task.cancel();
            }

        }, 1, 4, TimeUnit.MINUTES);
    }

    public void acceptInvite (ProxiedPlayer invitedPlayer, ProxiedPlayer leader) {
        partyInvites.remove(leader, invitedPlayer);
        leader.sendMessage(invitedPlayer.getName() + " hat deine Anfrage angenommen!");
        addPlayerToParty(leader, invitedPlayer);
    }

    public void declineInvite (ProxiedPlayer invitedPlayer, ProxiedPlayer leader) {
        partyInvites.remove(leader, invitedPlayer);
        leader.sendMessage(invitedPlayer.getName() + " hat deine Anfrage ABGELEHNT!");
    }


    public void broadcastPartyMembers (ProxiedPlayer leader) {
        ProxyServer.getInstance().broadcast("Leader: " + leader.getName() +  ", Memberz: " + partyStorage.get(leader).toString());
    }

}
