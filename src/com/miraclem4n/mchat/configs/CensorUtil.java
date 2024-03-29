package com.miraclem4n.mchat.configs;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class CensorUtil
{
  static YamlConfiguration config;
  static File file;

  public static void initialize()
  {
    load();
  }

  public static void dispose() {
    config = null;
    file = null;
  }

  public static void load() {
    file = new File("plugins/mChatSuite/censor.yml");

    config = YamlConfiguration.loadConfiguration(file);

    config.options().indent(4);
    config.options().header("MChat Censor");

    if (!file.exists())
      loadDefaults();
  }

  private static void loadDefaults() {
    set("fuck", "fawg");
    set("cunt", "punt");
    set("shit", "feces");
    set("dick", "LARGE PENIS");
    set("miracleman", "MiracleM4n");

    save();
  }

  public static void set(String key, Object obj) {
    config.set(key, obj);
  }

  public static Boolean save() {
    try {
      config.save(file);
      return Boolean.valueOf(true); } catch (Exception ignored) {
    }
    return Boolean.valueOf(false);
  }

  public static YamlConfiguration getConfig()
  {
    return config;
  }
}