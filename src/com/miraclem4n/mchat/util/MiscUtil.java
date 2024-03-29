package com.miraclem4n.mchat.util;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.config.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MiscUtil
{
  public static Boolean hasCommandPerm(CommandSender sender, String permission)
  {
    if (!API.checkPermissions(sender, permission).booleanValue()) {
      MessageUtil.sendMessage(sender, API.replace(LocaleType.MESSAGE_NO_PERMS.getVal(), "permission", permission, IndicatorType.LOCALE_VAR));
      return Boolean.valueOf(false);
    }

    return Boolean.valueOf(true);
  }

  public static Boolean isOnlineForCommand(CommandSender sender, String player) {
    if (Bukkit.getServer().getPlayer(player) == null) {
      MessageUtil.sendMessage(sender, "&4Player &e'" + player + "'&4 not Found.");
      return Boolean.valueOf(false);
    }

    return Boolean.valueOf(true);
  }

  public static Boolean isOnlineForCommand(CommandSender sender, Player player) {
    if (player == null) {
      MessageUtil.sendMessage(sender, "&4Player not Found.");
      return Boolean.valueOf(false);
    }

    return Boolean.valueOf(true);
  }
}