package de.fusion.deluxecounter.spigot.common;

import static net.minecraft.server.v1_13_R1.ChatMessageType.GAME_INFO;

import de.fusion.deluxecounter.spigot.DeluxeCounter;
import java.util.HashMap;
import net.minecraft.server.v1_13_R1.IChatBaseComponent;
import net.minecraft.server.v1_13_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBarAPI {

  final static HashMap<String, Integer> Count = new HashMap<String, Integer>();

  public static void sendActionBar(Player player, String Nachricht) {
    final String NachrichtNeu = Nachricht.replace("_", " ");
    String s = ChatColor.translateAlternateColorCodes('&', NachrichtNeu);
    IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s +
        "\"}");
    PacketPlayOutChat bar = new PacketPlayOutChat(icbc, GAME_INFO);
    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
  }

  public static void sendActionBarTime(final Player player, final String Nachricht,
      final Integer Zeit) {

    final String NachrichtNeu = Nachricht.replace("_", " ");

    if (!Count.containsKey(player.getName())) {
      String s = ChatColor.translateAlternateColorCodes('&', NachrichtNeu);
      IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s +
          "\"}");
      PacketPlayOutChat bar = new PacketPlayOutChat(icbc, GAME_INFO);
      ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
    }

    Bukkit.getScheduler().runTaskLater(DeluxeCounter.getInstance(), () -> {
      String s = ChatColor.translateAlternateColorCodes('&', NachrichtNeu);
      IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s +
          "\"}");
      PacketPlayOutChat bar = new PacketPlayOutChat(icbc, GAME_INFO);
      ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);

      if (!Count.containsKey(player.getName())) {
        Count.put(player.getName(), 0);
      }
      int count = Count.get(player.getName());
      int newCount = count + 20;
      Count.put(player.getName(), newCount);

      if (newCount < Zeit - 20) {
        wait(player, Nachricht, Zeit);
      } else {
        Count.remove(player.getName());
      }
    }, 10);
  }

  private static void wait(final Player player, final String Nachricht, final Integer Zeit) {
    Bukkit.getScheduler().runTaskLater(DeluxeCounter.getInstance(),
        () -> sendActionBarTime(player, Nachricht, Zeit), 10);

  }
}

