package de.fusion.deluxecounter;

import de.fusion.deluxecounter.config.ConfigManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeluxeCounter extends Plugin {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static DeluxeCounter instance;
    private static ConfigManager configuration;
    private boolean running;

    @Override
    public void onLoad() {
        super.onLoad();
        instance = this;
        running = true;
    }

    public void onEnable() {
        super.onEnable();
        File config = new File(getDataFolder(), "config.yml");
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        if (!config.exists())
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, config.toPath());
            } catch (IOException ignored) { }
            
        configuration = new ConfigManager(config).build();
        log("Loaded DeluxeCounter by FusionCoding");


        PluginManager pm = ProxyServer.getInstance().getPluginManager();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        running = false;
    }

    public void log(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(getPrefix() + message);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public static DeluxeCounter getInstance() {
        return instance;
    }

    public static ConfigManager getConfiguration() {
        return configuration;
    }

    public static String getPrefix() {
        return getConfiguration().getPath("General.Prefix").getString();
    }
}
