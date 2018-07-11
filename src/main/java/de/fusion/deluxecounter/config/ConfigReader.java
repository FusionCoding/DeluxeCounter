package de.fusion.deluxecounter.config;

import java.util.List;

public interface ConfigReader {


    boolean contains();

    int getInt();

    boolean getBoolean();

    String getString();

    List<?> getList();

    Object getObject();

}
