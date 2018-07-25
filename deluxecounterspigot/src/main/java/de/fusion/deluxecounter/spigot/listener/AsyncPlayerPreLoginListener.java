package de.fusion.deluxecounter.spigot.listener;

import de.fusion.deluxecounter.spigot.DeluxeCounter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class AsyncPlayerPreLoginListener implements Listener {

  @EventHandler
  public void on(AsyncPlayerPreLoginEvent e) {
    DeluxeCounter.getCountManager().addConnection();
    if (!(DeluxeCounter.getInstance().isAllowingConnections())) {
      e.setKickMessage(
          DeluxeCounter.getConfiguration().getPath("KickMessages.NotAllowing").getStringList());
      e.setLoginResult(Result.KICK_OTHER);

    }

  }


}
