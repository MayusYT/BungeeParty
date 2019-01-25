package net.snapecraft.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.snapecraft.util.PartyStorage;

public class ServerSwitchListener implements Listener {

    @EventHandler
    public void onServerChange(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();

        if(PartyStorage.getInstance().isPlayerInParty(p)) {
          if(PartyStorage.getInstance().doesPlayerOwnParty(p)) {
              for(ProxiedPlayer partyMember : PartyStorage.getInstance().getParty(p)) {
                  if(partyMember.getServer().getInfo() != p.getServer().getInfo()) {
                      partyMember.connect(p.getServer().getInfo());
                      partyMember.sendMessage("Du wurdest auf einen anderen Server gesendet, da deine Party diesen Server betreten hat!");
                  }
              }
          } else {
              p.sendMessage("!!ACHTUNG!! Du hast den Server gewechselt, ohne dass du der Anführer der Party bist!" +
                      " Deine Party ist dir NICHT nachgejoint!");
          }
        }
    }
}
