package de.fusion.deluxecounter.config;

import java.io.File;
import net.md_5.bungee.config.Configuration;

/**
 * Class providing File & Configuration
 */
abstract class ConfigAdapter {

  private final File f;
  private Configuration configuration;

  /**
   * Sets the file of the configuration
   */
  ConfigAdapter(File f) {
    this.f = f;
  }

  /**
   * Returns the file of the configuration
   *
   * @return File
   */
  File getFile() {
    return f;
  }

  /**
   * Returns the configuration as Object
   *
   * @return Configuration
   */
  Configuration getConfiguration() {
    return configuration;
  }

  /**
   * Sets the actual configuration
   */
  void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
}
