package com.miraclem4n.mchat.api;

import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;

public class Writer
{
  public static void addBase(String name, InfoType type)
  {
    String base = type.getName();

    if (type.equals(InfoType.USER)) {
      InfoUtil.getConfig().set(base + "." + name + ".group", ConfigType.INFO_DEFAULT_GROUP.getString());
    }
    InfoUtil.getConfig().set(base + "." + name + ".info.prefix", "");
    InfoUtil.getConfig().set(base + "." + name + ".info.suffix", "");

    save();

    if (type.equals(InfoType.USER))
      setDGroup(ConfigType.INFO_DEFAULT_GROUP.getString());
  }

  public static void addBase(String player, String group)
  {
    InfoUtil.getConfig().set("users." + player + ".group", group);
    InfoUtil.getConfig().set("users." + player + ".info.prefix", "");
    InfoUtil.getConfig().set("users." + player + ".info.suffix", "");

    save();

    setDGroup(group);
  }

  public static void addWorld(String name, InfoType type, String world)
  {
    String base = type.getName();

    if (!InfoUtil.getConfig().isSet(base + "." + name)) {
      addBase(name, type);
    }
    InfoUtil.getConfig().set(base + "." + name + ".worlds." + world + "prefix", "");
    InfoUtil.getConfig().set(base + "." + name + ".worlds." + world + "suffix", "");

    save();
  }

  public static void setInfoVar(String name, InfoType type, String var, Object value)
  {
    String base = type.getName();

    if (!InfoUtil.getConfig().isSet(base + "." + name)) {
      addBase(name, type);
    }
    InfoUtil.getConfig().set(base + "." + name + ".info." + var, value);

    save();
  }

  public static void setWorldVar(String name, InfoType type, String world, String var, Object value)
  {
    String base = type.getName();

    if (!InfoUtil.getConfig().isSet(base + "." + name + ".worlds." + world)) {
      addWorld(name, type, world);
    }
    InfoUtil.getConfig().set(base + "." + name + ".worlds." + world + "." + var, value);

    save();
  }

  public static void setGroup(String player, String group)
  {
    if (!InfoUtil.getConfig().isSet(player + "." + group)) {
      addBase(player, group);
    }
    InfoUtil.getConfig().set("users." + player + ".group", group);

    save();
  }

  public static void removeBase(String name, InfoType type)
  {
    String base = type.getName();

    if (InfoUtil.getConfig().isSet(base + "." + name)) {
      InfoUtil.getConfig().set(base + "." + name, null);

      save();
    }
  }

  public static void removeInfoVar(String name, InfoType type, String var)
  {
    setInfoVar(name, type, var, null);
  }

  public static void removeWorld(String name, InfoType type, String world)
  {
    String base = type.getName();

    if ((InfoUtil.getConfig().isSet(base + "." + name)) && 
      (InfoUtil.getConfig().isSet(base + "." + name + ".worlds." + world))) {
      InfoUtil.getConfig().set(base + "." + name + ".worlds." + world, null);

      save();
    }
  }

  public static void removeWorldVar(String name, InfoType type, String world, String var)
  {
    setWorldVar(name, type, world, var, null);
  }

  private static void setDGroup(String group) {
    if (!InfoUtil.getConfig().isSet("groups." + group)) {
      InfoUtil.getConfig().set("groups." + group + ".info.prefix", "");
      InfoUtil.getConfig().set("groups." + group + ".info.suffix", "");

      save();
    }
  }

  private static void save() {
    InfoUtil.save();
  }
}