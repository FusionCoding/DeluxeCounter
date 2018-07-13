package de.fusion.deluxecounter.common;

import de.fusion.deluxecounter.DeluxeCounter;
import java.util.concurrent.ConcurrentHashMap;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class CountManager {

  private long totalConnections = 0;
  private ConcurrentHashMap<Integer, Long> average = new ConcurrentHashMap<>();
  private int threads;


  public CountManager() {
    threads = DeluxeCounter.getConfiguration().getPath("General.CountThreads").getInt();
  }

  public void init() {
    long diff = 1000 / threads;
    DeluxeCounter.getInstance().getMainExecutorService().submit(() -> {
      for (int i = 1; i <= threads; i++) {
        CounterThread counterThread = new CounterThread(i);
        counterThread.setName("CounterThread#" + i);
        counterThread.start();
        try {
          Thread.sleep(diff);
        } catch (InterruptedException ignored) {
        }
      }
      DeluxeCounter.getInstance().log("Started " + threads + " counter threads.");
    });

    new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }

      while (DeluxeCounter.getInstance().isRunning()) {
        ProxyServer.getInstance().getPlayers().forEach(proxiedPlayer -> {
          if (DeluxeCounter.getInstance().getToggledPlayers().contains(proxiedPlayer.getName())) {
            proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                DeluxeCounter.getConfiguration().getPath("General.ActionBar").getString()
                    .replace("%prefix%", DeluxeCounter.getPrefix())
                    .replace("%amount%", getBotsPerSecond() + "")));
          }
        });
        try {
          Thread.sleep(20);
        } catch (InterruptedException ignored) {
        }
      }
    }, "DeluxeCounter#Notificator").start();
  }

  public void addConnection() {
    totalConnections++;
  }

  public void submit(int number, long amount) {
    average.put(number, amount);
  }

  public long getTotalConnections() {
    return totalConnections;
  }

  private long getBotsPerSecond() {
    long total = 0;
    for (int i = 1; i <= threads; i++) {
      total += average.get(i);
    }

    return total / threads;
  }
}
