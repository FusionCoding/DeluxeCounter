package de.fusion.deluxecounter.listener;

import de.fusion.deluxecounter.DeluxeCounter;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * This class is used to count
 */
public class PreLoginListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PreLoginEvent e) {
        e.registerIntent(DeluxeCounter.getInstance());
        DeluxeCounter.getCountManager().addConnection();
        if (!(DeluxeCounter.getInstance().isAllowingConnections())) {
            e.setCancelReason(new TextComponent(DeluxeCounter.getConfiguration().getPath("KickMessages.NotAllowing").getStringList()));
            e.setCancelled(true);
        }
        e.completeIntent(DeluxeCounter.getInstance());

    }

}
