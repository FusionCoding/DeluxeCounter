package de.fusion.deluxecounter.spigot;


import de.fusion.deluxecounter.spigot.commands.DeluxeCounterCommand;
import de.fusion.deluxecounter.spigot.common.CountManager;
import de.fusion.deluxecounter.spigot.config.ConfigManager;
import de.fusion.deluxecounter.spigot.listener.AsyncPlayerPreLoginListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * This is the main class initializing everything
 */

public class DeluxeCounter extends JavaPlugin {

  private ExecutorService executorService = Executors.newCachedThreadPool();
  private static DeluxeCounter instance;
  private static ConfigManager configuration;
  private static CountManager countManager;
  private boolean running;
  private List<String> toggledPlayers = new ArrayList<>();
  private boolean allowConnections = true;


  /**
   * Used to initialize the instance of the class
   */
  @Override
  public void onLoad() {
    super.onLoad();
    instance = this;
    running = true;
  }

  /**
   * Used to manage the configuration and everything else necessary.
   */
  public void onEnable() {
    super.onEnable();
    File config = new File(getDataFolder(), "config.yml");
    if (!getDataFolder().exists()) {
      getDataFolder().mkdir();
    }
    if (!config.exists()) {
      saveResource("config.yml", true);
    }

    configuration = new ConfigManager(config).build();
    log("Loaded DeluxeCounter by FusionCoding");

    countManager = new CountManager();
    countManager.init();

    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new AsyncPlayerPreLoginListener(), this);
    getCommand("deluxecounter").setExecutor(new DeluxeCounterCommand());


  }

  /**
   * Used to disable all running tasks listening for running
   */
  @Override
  public void onDisable() {
    super.onDisable();
    running = false;
  }

  /**
   * This is used to log something to the console Automatically adds the prefix
   *
   * @param message to print
   */
  public void log(String message) {
    Bukkit.getConsoleSender().sendMessage(getPrefix() + message);
  }

  /**
   * Method is used to easily run asynchronous tasks
   *
   * @return ExecutorService
   */
  public ExecutorService getMainExecutorService() {
    return executorService;
  }

  /**
   * This Method returns the instance of the plugin
   *
   * @return DeluxeCounter
   */
  public static DeluxeCounter getInstance() {
    return instance;
  }

  /**
   * Method will return a instance of 'ConfigManager' This allows easy access to the configuration
   * file
   *
   * @return ConfigManager
   */
  public static ConfigManager getConfiguration() {
    return configuration;
  }

  /**
   * Method will return the String of the plugin given in the configuration file
   *
   * @return String of DeluxeCounter
   */
  public static String getPrefix() {
    return getConfiguration().getPath("General.Prefix").getString();
  }

  /**
   * Method returns the current state of the active plugin
   *
   * @return boolean
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Method returns the CountManager Can be used to receive the current bots per second
   *
   * @return CountManager
   */
  public static CountManager getCountManager() {
    return countManager;
  }

  /**
   * Method can be used to access the toggled players
   *
   * @return List
   */
  public List<String> getToggledPlayers() {
    return toggledPlayers;
  }

  /**
   * Used to determine if connections should be allowed Useful for testing
   *
   * @return boolean
   */
  public boolean isAllowingConnections() {
    return allowConnections;
  }

  /**
   * Used to set the status of allowing connections
   */
  public void setAllowConnections(boolean allowConnections) {
    this.allowConnections = allowConnections;
  }
}
