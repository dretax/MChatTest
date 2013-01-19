package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener
  implements Listener
{
  MChat plugin;

  public ChatListener(MChat instance)
  {
    this.plugin = instance;
  }

  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerChat(AsyncPlayerChatEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    Player player = event.getPlayer();
    String pName = player.getName();

    String world = player.getWorld().getName();
    String msg = event.getMessage();
    String eventFormat = Parser.parseChatMessage(pName, world, msg);

    if (msg == null) {
      return;
    }
    setListName(player, Parser.parseTabbedList(pName, world));

    if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble().doubleValue() > 0.0D) {
      for (Player players : this.plugin.getServer().getOnlinePlayers()) {
        if ((players.getWorld() != player.getWorld()) || (players.getLocation().distance(player.getLocation()) > ConfigType.MCHAT_CHAT_DISTANCE.getDouble().doubleValue()))
        {
          if (isSpy(players.getName(), players.getWorld().getName()).booleanValue()) {
            players.sendMessage(eventFormat.replace(LocaleType.FORMAT_LOCAL.getVal(), LocaleType.FORMAT_FORWARD.getVal()));
          }
          event.getRecipients().remove(players);
        }
      }
    }
    event.setFormat(eventFormat);
  }

  Boolean isSpy(String player, String world) {
    if (API.checkPermissions(player, world, "mchat.spy").booleanValue()) {
      MChat.spying.put(player, Boolean.valueOf(true));
      return Boolean.valueOf(true);
    }

    MChat.spying.put(player, Boolean.valueOf(false));
    return Boolean.valueOf(false);
  }

  void setListName(Player player, String listName) {
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