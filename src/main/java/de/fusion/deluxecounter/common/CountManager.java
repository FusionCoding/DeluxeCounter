package de.fusion.deluxecounter.common;

import de.fusion.deluxecounter.DeluxeCounter;

import java.util.concurrent.ConcurrentHashMap;

public class CountManager {

    private long totalConnections = 0;
    private ConcurrentHashMap<Integer, Long> average = new ConcurrentHashMap<Integer, Long>();
    int threads;


    public CountManager() {
        threads = DeluxeCounter.getConfiguration().getPath("General.CountThreads").getInt();
        long diff = 1000 / threads;
        DeluxeCounter.getInstance().getExecutorService().submit(() -> {
            for (int i = 1; i <= threads; i++) {
                CounterThread counterThread = new CounterThread(i);
                counterThread.setName("CounterThread#" + i);
                counterThread.start();
                try {
                    Thread.sleep(diff);
                } catch (InterruptedException ignored) {
                }
            }
        });
        DeluxeCounter.getInstance().log("Started " + threads + " counter threads.");
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

    public long getBotsPerSecond() {
        long total = 0;
        for (int i = 1; i <= threads; i++) {
            total += average.get(i);
        }

        return total / threads;
    }
}
