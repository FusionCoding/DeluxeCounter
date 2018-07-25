package de.fusion.deluxecounter.bungee.config;


import de.fusion.deluxecounter.bungee.DeluxeCounter;
import de.fusion.deluxecounter.config.ConfigAdapter;
import de.fusion.deluxecounter.config.ConfigAdapterInterface;
import de.fusion.deluxecounter.config.ConfigReader;
import de.fusion.deluxecounter.config.ConfigWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * This class is the main ConfigManager for BungeeCord This class handles everything and sets the
 * actual methods
 */

public class ConfigManager extends ConfigAdapter implements ConfigAdapterInterface {


  /**
   * Initialize the ConfigManager
   *
   * @param f as configuration File
   */
  public ConfigManager(File f) {
    super(f);
  }

  /**
   * Method builds the ConfigManager
   */
  public ConfigManager build() {
    reload();
    return this;
  }

  /**
   * Method reloads the configuration
   *
   * @return ConfigManager
   */
  public ConfigManager reload() {
    try {
      setConfiguration(ConfigurationProvider.getProvider(YamlConfiguration.class).load(getFile()));
    } catch (IOException ignored) {
      ignored.printStackTrace();
    }
    return this;
  }

  /**
   * Method to set the path which needs to be written to
   *
   * @return ConfigWriter
   */
  @Override
  public ConfigWriter setPath(String path) {
    return new RealConfigWriter((Configuration) getConfiguration(), path);
  }

  /**
   * Method to set the path which needs to be read.
   *
   * @return ConfigReader
   */
  @Override
  public ConfigReader getPath(String path) {
    return new RealConfigReader((Configuration) getConfiguration(), path);
  }

  /**
   * Class defining methods to read the configuration
   */
  private class RealConfigReader implements ConfigReader {

    private final Configuration configuration;
    private final String path;

    /**
     * Initializes the config reader
     */
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
    public String getStringList() {
      StringBuilder stringList = new StringBuilder();
      getList().forEach(string -> stringList.append(string).append("\n"));
      return ChatColor.translateAlternateColorCodes('&', stringList.toString());
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

  /**
   * Class defining methods to read the configuration
   */
  private class RealConfigWriter implements ConfigWriter {

    private final Configuration configuration;
    private final String path;

    /**
     * Initializes the config reader
     */
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
      DeluxeCounter.getInstance().getMainExecutorService().submit(() -> this.setObject(o));
    }
  }
}
