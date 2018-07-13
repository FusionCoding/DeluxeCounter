package de.fusion.deluxecounter.config;

public interface ConfigAdapterInterface {


  ConfigManager build();

  ConfigManager reload();

  ConfigWriter setPath(String path);

  ConfigReader getPath(String path);


}
