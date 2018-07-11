package de.fusion.deluxecounter.config;


import de.fusion.deluxecounter.DeluxeCounter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager extends ConfigAdapter implements ConfigAdapterInterface {


    public ConfigManager(File f) {
        super(f);
    }

    @Override
    public ConfigManager build() {
        reload();
        return this;
    }

    @Override
    public ConfigManager reload() {
        try {
            setConfiguration(ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile()));
        } catch (IOException ignored) { }
        return this;
    }

    @Override
    public ConfigWriter setPath(String path) {
        return new RealConfigWriter(getConfiguration(), path);
    }

    @Override
    public ConfigReader getPath(String path) {
        return new RealConfigReader(getConfiguration(), path);
    }


    private class RealConfigReader implements ConfigReader {

        private final Configuration configuration;
        private final String path;

        private RealConfigReader(Configuration configuration, String path) {
            this.configuration = configuration;
            this.path = path;
        }

        @Override
        public boolean contains() {
            return configuration.contains(path);
        }

        @Override
        public int getInt() {
            return configuration.getInt(path);
        }

        @Override
        public boolean getBoolean() {
            return configuration.getBoolean(path);
        }

        @Override
        public String getString() {
            return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
        }

        @Override
        public List<?> getList() {
            return configuration.getList(path);
        }

        @Override
        public Object getObject() {
            return configuration.get(path);
        }
    }

    private class RealConfigWriter implements ConfigWriter {
        private final Configuration configuration;
        private final String path;

        private RealConfigWriter(Configuration configuration, String path) {
            this.configuration = configuration;
            this.path = path;
        }

        @Override
        public void setObject(Object o) {
            configuration.set(path, o);
        }

        @Override
        public void setAsyncObject(Object o) {
            DeluxeCounter.getInstance().getExecutorService().submit(() -> this.setObject(o));
        }
    }
}
