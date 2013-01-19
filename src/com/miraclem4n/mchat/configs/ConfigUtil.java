package com.miraclem4n.mchat.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class ConfigUtil
{
  static YamlConfiguration config;
  static File file;
  static Boolean changed;
  static ArrayList<String> meAliases = new ArrayList<String>();

  static HashMap<String, List<String>> aliasMap = new HashMap<String, List<String>>();

  public static void initialize() {
    load();
  }

  public static void dispose() {
    config = null;
    file = null;
    changed = null;
    aliasMap = null;
  }

  public static void load() {
    file = new File("plugins/mChatSuite/config.yml");

    config = YamlConfiguration.loadConfiguration(file);

    config.options().indent(4);
    config.options().header("MChat Config");

    changed = Boolean.valueOf(false);

    loadDefaults();
  }

  private static void loadDefaults() {
    removeOption("auto-Changed");
    removeOption("mchat.suppressMessages");

    removeOption("mchat.enableList");

    editOption("mchat-message-format", "format.chat");
    editOption("mchat-API-only", "mchat.apiOnly");
    editOption("mchat-format-events", "mchat.formatEvents");
    editOption("mchat-chat-distance", "mchat.chatDistance");
    editOption("mchat-info-only", "info.useNewInfo");
    editOption("mchat-oldNodes-only", "info.useOldNodes");
    editOption("mchat-add-info-players", "info.addNewPlayers");

    editOption("mchat.formatEvents", "mchat.alter.events");

    editOption("mchat.alterEvents", "mchat.alter.events");
    editOption("mchat.alterDeathMessages", "mchat.alter.death");

    checkOption("format.chat", "+p+dn+s&f: +m");

    checkOption("mchat.apiOnly", Boolean.valueOf(false));
    checkOption("mchat.alter.events", Boolean.valueOf(true));
    checkOption("mchat.alter.death", Boolean.valueOf(true));
    checkOption("mchat.chatDistance", Double.valueOf(-1.0D));
    checkOption("mchat.varIndicator", "+");
    checkOption("mchat.cusVarIndicator", "-");
    checkOption("mchat.localeVarIndicator", "%");
    checkOption("mchat.spout", Boolean.valueOf(true));
    checkOption("mchat.IPCensor", Boolean.valueOf(true));
    checkOption("mchat.cLockRange", Integer.valueOf(3));

    checkOption("suppress.useDeath", Boolean.valueOf(false));
    checkOption("suppress.useJoin", Boolean.valueOf(false));
    checkOption("suppress.useKick", Boolean.valueOf(false));
    checkOption("suppress.useQuit", Boolean.valueOf(false));
    checkOption("suppress.maxDeath", Integer.valueOf(30));
    checkOption("suppress.maxJoin", Integer.valueOf(30));
    checkOption("suppress.maxKick", Integer.valueOf(30));
    checkOption("suppress.maxQuit", Integer.valueOf(30));

    checkOption("info.useNewInfo", Boolean.valueOf(false));
    checkOption("info.useLeveledNodes", Boolean.valueOf(false));
    checkOption("info.useOldNodes", Boolean.valueOf(false));
    checkOption("info.addNewPlayers", Boolean.valueOf(false));
    checkOption("info.defaultGroup", "default");

    loadAliases();

    checkOption("aliases.mchatme", meAliases);

    setupAliasMap();

    save();
  }

  public static void set(String key, Object obj) {
    config.set(key, obj);

    changed = Boolean.valueOf(true);
  }

  public static Boolean save() {
    if (!changed.booleanValue())
      return Boolean.valueOf(false);
    try
    {
      config.save(file);
      changed = Boolean.valueOf(false);
      return Boolean.valueOf(true); } catch (Exception ignored) {
    }
    return Boolean.valueOf(false);
  }

  public static YamlConfiguration getConfig()
  {
    return config;
  }

  public static HashMap<String, List<String>> getAliasMap() {
    return aliasMap;
  }

  private static void checkOption(String option, Object defValue) {
    if (!config.isSet(option))
      set(option, defValue);
  }

  private static void editOption(String oldOption, String newOption) {
    if (config.isSet(oldOption)) {
      set(newOption, config.get(oldOption));
      set(oldOption, null);
    }
  }

  private static void removeOption(String option) {
    if (config.isSet(option))
      set(option, null);
  }

  private static void loadAliases() {
    meAliases.add("me");
  }

  private static void setupAliasMap()
  {
    Set<String> keys = config.getConfigurationSection("aliases").getKeys(false);

    for (String key : keys)
      aliasMap.put(key, config.getStringList("aliases." + key));
  }
}