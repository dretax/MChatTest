package com.miraclem4n.mchat;

import com.herocraftonline.heroes.Heroes;
import com.massivecraft.factions.Conf;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.commands.InfoAlterCommand;
import com.miraclem4n.mchat.commands.MChatCommand;
import com.miraclem4n.mchat.commands.MeCommand;
import com.miraclem4n.mchat.configs.CensorUtil;
import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.configs.LocaleUtil;
import com.miraclem4n.mchat.events.ChatListener;
import com.miraclem4n.mchat.events.CommandListener;
import com.miraclem4n.mchat.events.EntityListener;
import com.miraclem4n.mchat.events.PlayerListener;
import com.miraclem4n.mchat.metrics.Metrics;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.TimerUtil;
import java.util.HashMap;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class MChat extends JavaPlugin
{
  public PluginManager pm;
  public PluginDescriptionFile pdfFile;
  public Boolean factions = Boolean.valueOf(false);
  public Heroes heroes;
  public Boolean heroesB = Boolean.valueOf(false);

  public Boolean towny = Boolean.valueOf(false);

  public Boolean mSocial = Boolean.valueOf(false);

  Boolean debug = Boolean.valueOf(false);
  public Metrics metrics;
  public static HashMap<String, Boolean> shouting;
  public static HashMap<String, Boolean> spying;
  public static String shoutFormat;

  public void onEnable()
  {
    TimerUtil timer = new TimerUtil();

    this.pm = getServer().getPluginManager();
    this.pdfFile = getDescription();

    killEss();

    shouting = new HashMap<String, Boolean>();
    spying = new HashMap<String, Boolean>();
    shoutFormat = "";

    initializeConfigs();

    setupPlugins();

    initializeClasses();

    registerEvents();

    setupCommands();

    if (ConfigType.INFO_ADD_NEW_PLAYERS.getBoolean().booleanValue()) {
      for (Player players : getServer().getOnlinePlayers()) {
        if (InfoUtil.getConfig().get("users." + players.getName()) == null)
          Writer.addBase(players.getName(), ConfigType.INFO_DEFAULT_GROUP.getString());
      }
    }
    timer.stop();

    long diff = timer.difference();

    MessageUtil.log("[" + this.pdfFile.getName() + "] " + this.pdfFile.getName() + " v" + this.pdfFile.getVersion() + " is enabled! [" + diff + "ms]");
  }

  public void onDisable()
  {
    TimerUtil timer = new TimerUtil();

    getServer().getScheduler().cancelTasks(this);

    shouting = null;
    spying = null;
    shoutFormat = null;

    unloadConfigs();

    timer.stop();

    long diff = timer.difference();

    MessageUtil.log("[" + this.pdfFile.getName() + "] " + this.pdfFile.getName() + " v" + this.pdfFile.getVersion() + " is disabled! [" + diff + "ms]");
  }

  void registerEvents() {
    if (!ConfigType.MCHAT_API_ONLY.getBoolean().booleanValue()) {
      this.pm.registerEvents(new PlayerListener(this), this);

      this.pm.registerEvents(new EntityListener(this), this);

      this.pm.registerEvents(new ChatListener(this), this);

      this.pm.registerEvents(new CommandListener(), this);
    }
  }

  Boolean setupPlugin(String pluginName) {
    Plugin plugin = this.pm.getPlugin(pluginName);

    if (plugin != null) {
      MessageUtil.logFormatted("<Plugin> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
      return Boolean.valueOf(true);
    }

    return Boolean.valueOf(false);
  }

  void setupPlugins()
  {
    this.factions = setupPlugin("Factions");

    if (this.factions.booleanValue()) {
      setupFactions();
    }

    this.heroesB = setupPlugin("Heroes");

    this.mSocial = setupPlugin("MSocial");

    if (this.heroesB.booleanValue()) {
      this.heroes = ((Heroes)this.pm.getPlugin("Heroes"));
    }
    this.towny = setupPlugin("Towny");
  }

  void setupFactions() {
    try {
      if (Conf.chatTagInsertIndex != 0)
        getServer().dispatchCommand(getServer().getConsoleSender(), "f config chatTagInsertIndex 0"); 
    } catch (NoSuchFieldError ignored) {
    }
  }

  void killEss() { Plugin plugin = this.pm.getPlugin("EssentialsChat");

    if (plugin != null)
      this.pm.disablePlugin(plugin); }

  public void initializeConfigs()
  {
    ConfigUtil.initialize();
    InfoUtil.initialize();
    CensorUtil.initialize();
    LocaleUtil.initialize();
  }

  public void reloadConfigs() {
    ConfigUtil.initialize();
    InfoUtil.initialize();
    CensorUtil.initialize();
    LocaleUtil.initialize();
  }

  public void unloadConfigs() {
    ConfigUtil.dispose();
    InfoUtil.dispose();
    CensorUtil.dispose();
    LocaleUtil.dispose();
  }

  void setupCommands() {
    regCommands("mchat", new MChatCommand(this));

    regCommands("mchatuser", new InfoAlterCommand(this, "mchatuser", InfoType.USER));
    regCommands("mchatgroup", new InfoAlterCommand(this, "mchatgroup", InfoType.GROUP));

    regCommands("mchatme", new MeCommand(this));
  }

  void regCommands(String command, CommandExecutor executor) {
    if (getCommand(command) != null)
      getCommand(command).setExecutor(executor);
  }

  void initializeClasses() {
    API.initialize();
    Parser.initialize(this);
  }
}