package com.miraclem4n.mchat.configs;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class LocaleUtil
{
  static YamlConfiguration config;
  static File file;
  static Boolean changed;

  public static void initialize()
  {
    load();
  }

  public static void dispose() {
    config = null;
    file = null;
    changed = null;
  }

  public static void load() {
    file = new File("plugins/mChatSuite/locale.yml");

    config = YamlConfiguration.loadConfiguration(file);

    config.options().indent(4);
    config.options().header("MChat Locale");

    changed = Boolean.valueOf(false);

    loadDefaults();
  }

  private static void loadDefaults() {
    checkOption("format.forward", "[F]");
    checkOption("format.local", "[L]");
    checkOption("format.spy", "[Spy]");

    checkOption("format.date", "HH:mm:ss");
    checkOption("format.name", "+p+dn+s&e");
    checkOption("format.tabbedList", "+p+dn+s");
    checkOption("format.listCmd", "+p+dn+s");
    checkOption("format.me", "* +p+dn+s&e +m");

    checkOption("message.config.reloaded", "%config Reloaded.");
    checkOption("message.config.updated", "%config has been updated.");
    checkOption("message.general.noPerms", "You do not have '%permission'.");
    checkOption("message.info.alteration", "Info Alteration Successful.");
    checkOption("message.player.notFound", "");
    checkOption("message.player.notOnline", "");
    checkOption("message.player.stillAfk", "You are still AFK.");
    checkOption("message.event.join", "%player has joined the game.");
    checkOption("message.event.leave", "%player has left the game.");
    checkOption("message.event.kick", "%player has been kicked from the game. [ %reason ]");
    checkOption("message.death.inFire", "%player went up in flames.");
    checkOption("message.death.onFire", "%player burned to death.");
    checkOption("message.death.lava", "%player tried to swim in lava.");
    checkOption("message.death.inWall", "%player suffocated in a wall.");
    checkOption("message.death.drown", "%player drowned.");
    checkOption("message.death.starve", "%player starved to death.");
    checkOption("message.death.cactus", "%player was pricked to death.");
    checkOption("message.death.fall", "%player hit the ground too hard.");
    checkOption("message.death.outOfWorld", "%player fell out of the world.");
    checkOption("message.death.generic", "%player died.");
    checkOption("message.death.explosion", "%player blew up.");
    checkOption("message.death.magic", "%player was killed by magic.");
    checkOption("message.death.entity", "%player was slain by %killer.");
    checkOption("message.death.arrow", "%player was shot by %killer.");
    checkOption("message.death.fireball", "%player was fireballed by %killer.");
    checkOption("message.death.thrown", "%player was pummeled by %killer.");
    checkOption("message.heroes.isMaster", "The Great");
    checkOption("message.heroes.notMaster", "The Squire");

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

  private static void checkOption(String option, Object defValue) {
    if (!config.isSet(option))
      set(option, defValue);
  }
}