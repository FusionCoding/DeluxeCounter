package de.fusion.deluxecounter.config;

import net.md_5.bungee.config.Configuration;

import java.io.File;

public abstract class ConfigAdapter {

    private final File f;
    private Configuration configuration;

    protected ConfigAdapter(File f) {
        this.f = f;
    }

    public File getFile() {
        return f;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
