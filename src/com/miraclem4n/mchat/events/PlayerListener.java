package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerListener
  implements Listener
{
  MChat plugin;

  public PlayerListener(MChat instance)
  {
    this.plugin = instance;
  }

  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final String world = player.getWorld().getName();

    final String pName = player.getName();
    String msg = event.getJoinMessage();

    if (msg == null) {
      return;
    }

    if ((ConfigType.INFO_ADD_NEW_PLAYERS.getBoolean().booleanValue()) && 
      (InfoUtil.getConfig().get("users." + pName) == null)) {
      Writer.addBase(pName, ConfigType.INFO_DEFAULT_GROUP.getString());
    }
    this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
      public void run() {
        PlayerListener.this.setListName(player, Parser.parseTabbedList(pName, world));
      }
    }
    , 20L);

    if (ConfigType.MCHAT_ALTER_EVENTS.getBoolean().booleanValue())
      if (ConfigType.SUPPRESS_USE_JOIN.getBoolean().booleanValue()) {
        suppressEventMessage(Parser.parseEvent(pName, world, EventType.JOIN), "mchat.suppress.join", "mchat.bypass.suppress.join", ConfigType.SUPPRESS_MAX_JOIN.getInteger());
        event.setJoinMessage(null);
      } else {
        event.setJoinMessage(Parser.parseEvent(pName, world, EventType.JOIN));
      }
  }

  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerKick(PlayerKickEvent event) { if (!ConfigType.MCHAT_ALTER_EVENTS.getBoolean().booleanValue()) {
      return;
    }
    if (event.isCancelled()) {
      return;
    }
    String pName = event.getPlayer().getName();
    String world = event.getPlayer().getWorld().getName();
    String msg = event.getLeaveMessage();

    if (msg == null) {
      return;
    }
    String reason = event.getReason();

    String kickMsg = Parser.parseEvent(pName, world, EventType.KICK).replace(ConfigType.MCHAT_VAR_INDICATOR.getString() + "reason", reason).replace(ConfigType.MCHAT_VAR_INDICATOR.getString() + "r", reason);

    if (ConfigType.SUPPRESS_USE_KICK.getBoolean().booleanValue()) {
      suppressEventMessage(kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick", ConfigType.SUPPRESS_MAX_KICK.getInteger());
      event.setLeaveMessage(null);
    } else {
      event.setLeaveMessage(kickMsg);
    } }

  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent event) {
    String pName = event.getPlayer().getName();
    String world = event.getPlayer().getWorld().getName();
    String msg = event.getQuitMessage();

    if (!ConfigType.MCHAT_ALTER_EVENTS.getBoolean().booleanValue()) {
      return;
    }
    if (msg == null) {
      return;
    }
    if (ConfigType.SUPPRESS_USE_QUIT.getBoolean().booleanValue()) {
      suppressEventMessage(Parser.parseEvent(pName, world, EventType.QUIT), "mchat.suppress.quit", "mchat.bypass.suppress.quit", ConfigType.SUPPRESS_MAX_QUIT.getInteger());
      event.setQuitMessage(null);
    } else {
      event.setQuitMessage(Parser.parseEvent(pName, world, EventType.QUIT));
    }
  }

  void suppressEventMessage(String format, String permNode, String overrideNode, Integer max) { for (Player player : this.plugin.getServer().getOnlinePlayers()) {
      if (API.checkPermissions(player.getName(), player.getWorld().getName(), overrideNode).booleanValue()) {
        player.sendMessage(format);
      }
      else if ((this.plugin.getServer().getOnlinePlayers().length <= max.intValue()) && 
        (!API.checkPermissions(player.getName(), player.getWorld().getName(), permNode).booleanValue())) {
        player.sendMessage(format);
      }
    }
    MessageUtil.log(format); }

  void setListName(Player player, String listName)
  {
    if (listName.length() > 15) {
      listName = listName.substring(0, 16);
      player.setPlayerListName(listName);
    }
    try
    {
      player.setPlayerListName(listName);
    }
    catch (Exception ignored)
    {
    }
  }
}