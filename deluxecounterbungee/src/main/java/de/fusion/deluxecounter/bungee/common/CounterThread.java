package de.fusion.deluxecounter.bungee.common;

import de.fusion.deluxecounter.bungee.DeluxeCounter;

public class CounterThread extends Thread {

  private int number;

  CounterThread(int number) {
    this.number = number;
  }

  @Override
  public void run() {
    super.run();
    long botsBefore = 0;
    while (DeluxeCounter.getInstance().isRunning()) {
      DeluxeCounter.getCountManager()
          .submit(number, DeluxeCounter.getCountManager().getTotalConnections() - botsBefore);
      botsBefore = DeluxeCounter.getCountManager().getTotalConnections();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }
  }
}
