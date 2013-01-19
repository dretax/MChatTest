package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.configs.CensorUtil;
import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.configs.LocaleUtil;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class MChatCommand
  implements CommandExecutor
{
  MChat plugin;

  public MChatCommand(MChat instance)
  {
    this.plugin = instance;
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!command.getName().equalsIgnoreCase("mchat")) {
      return true;
    }
    if (args.length == 0) {
      return false;
    }
    if (args[0].equalsIgnoreCase("version")) {
      if (!MiscUtil.hasCommandPerm(sender, "mchat.version").booleanValue()) {
        return true;
      }
      String cVersion = "&6MineCraft Version: &2" + this.plugin.pdfFile.getVersion();

      cVersion = cVersion.replaceFirst("-", "^*^&6Jenkins Build &5#&5: &2");
      cVersion = cVersion.replaceFirst("-", "^*^&6Release Version: &2");
      cVersion = cVersion.replaceFirst("_", "^*^&6Fix &5#&5: &2");

      String[] vArray = cVersion.split("\\^\\*\\^");

      MessageUtil.sendMessage(sender, "&6Full Version: &1" + this.plugin.pdfFile.getVersion());

      for (String string : vArray) {
        MessageUtil.sendMessage(sender, string);
      }
      return true;
    }if ((args[0].equalsIgnoreCase("reload")) || (args[0].equalsIgnoreCase("r")))
    {
      if (args.length > 1) {
        if ((args[1].equalsIgnoreCase("config")) || (args[1].equalsIgnoreCase("co")))
        {
          if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.config").booleanValue()) {
            return true;
          }
          ConfigUtil.initialize();
          MessageUtil.sendMessage(sender, "Config Reloaded.");
          return true;
        }if ((args[1].equalsIgnoreCase("info")) || (args[1].equalsIgnoreCase("i")))
        {
          if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.info").booleanValue()) {
            return true;
          }
          InfoUtil.initialize();
          MessageUtil.sendMessage(sender, "Info Reloaded.");
          return true;
        }if ((args[1].equalsIgnoreCase("censor")) || (args[1].equalsIgnoreCase("ce")))
        {
          if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.censor").booleanValue()) {
            return true;
          }
          CensorUtil.initialize();
          MessageUtil.sendMessage(sender, "Censor Reloaded.");
          return true;
        }if ((args[1].equalsIgnoreCase("locale")) || (args[1].equalsIgnoreCase("l")))
        {
          if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.locale").booleanValue()) {
            return true;
          }
          LocaleUtil.initialize();
          MessageUtil.sendMessage(sender, "Locale Reloaded.");
          return true;
        }if ((args[1].equalsIgnoreCase("all")) || (args[1].equalsIgnoreCase("a")))
        {
          if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.all").booleanValue()) {
            return true;
          }
          this.plugin.reloadConfigs();
          this.plugin.initializeConfigs();
          MessageUtil.sendMessage(sender, "All Config's Reloaded.");
          return true;
        }
      }
    }
    return false;
  }
}