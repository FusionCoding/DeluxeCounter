package de.fusion.deluxecounter.config;

import net.md_5.bungee.config.Configuration;

import java.io.File;

/**
 * Class providing File & Configuration
 */
public abstract class ConfigAdapter {

    private final File f;
    private Configuration configuration;

    /**
     * Sets the file of the configuration
     * @param f
     */
    protected ConfigAdapter(File f) {
        this.f = f;
    }

    /**
     * Returns the file of the configuration
     * @return File
     */
    public File getFile() {
        return f;
    }

    /**
     * Returns the configuration as Object
     * @return Configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the actual configuration
     * @param configuration
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
