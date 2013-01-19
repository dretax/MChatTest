package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.types.InfoEditType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoAlterCommand
  implements CommandExecutor
{
  MChat plugin;
  InfoType type;
  String cmd;

  public InfoAlterCommand(MChat instance, String command, InfoType infoType)
  {
    this.plugin = instance;
    this.type = infoType;
    this.cmd = command;
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!command.getName().equalsIgnoreCase(this.cmd)) {
      return true;
    }
    if (args.length == 0) {
      MessageUtil.sendMessage(sender, "Use '/" + this.cmd + " add/edit/remove' for more info.");
      return true;
    }

    String p = "user";
    String t = "player";
    String T = "Player";

    if (this.type == InfoType.GROUP) {
      p = "group";
      t = "group";
      T = "Group";
    }

    if ((args[0].equalsIgnoreCase("a")) || (args[0].equalsIgnoreCase("add")))
    {
      if (args.length == 1) {
        MessageUtil.sendMessage(sender, "Usage for '/" + this.cmd + " add':\n" + "    - /" + this.cmd + " add " + t + " <" + T + ">\n" + "    - /" + this.cmd + " add ivar <" + T + "> <Variable> [Value]\n" + "    - /" + this.cmd + " add world <" + T + "> <World>\n" + "    - /" + this.cmd + " add wvar <" + T + "> <World> <Variable> [Value]");

        return true;
      }if ((args[1].equalsIgnoreCase(t.substring(0, 1))) || (args[1].equalsIgnoreCase(t)))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add." + t).booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.ADD_BASE;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.addBase(args[2], this.type);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }if ((args[1].equalsIgnoreCase("iVar")) || (args[1].equalsIgnoreCase("infoVariable")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add.ivar").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.ADD_INFO_VAR;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.setInfoVar(args[2], this.type, args[3], combineArgs(args, Integer.valueOf(4)));
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }if ((args[1].equalsIgnoreCase("w")) || (args[1].equalsIgnoreCase("world")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add.world").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.ADD_WORLD;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.addWorld(args[2], this.type, args[3]);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }if ((args[1].equalsIgnoreCase("wVar")) || (args[1].equalsIgnoreCase("worldVariable")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add.wvar").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.ADD_WORLD_VAR;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.setWorldVar(args[2], this.type, args[3], args[4], combineArgs(args, Integer.valueOf(5)));
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }
    } else if ((args[0].equalsIgnoreCase("r")) || (args[0].equalsIgnoreCase("remove")))
    {
      if (args.length == 1) {
        MessageUtil.sendMessage(sender, "Usage for '/" + this.cmd + " remove':\n" + "    - /" + this.cmd + " remove " + t + " <" + T + ">\n" + "    - /" + this.cmd + " remove ivar <" + T + "> <Variable>\n" + "    - /" + this.cmd + " remove world <" + T + "> <World>\n" + "    - /" + this.cmd + " remove wvar <" + T + "> <World> <Variable>");

        return true;
      }if ((args[1].equalsIgnoreCase(t.substring(0, 1))) || (args[1].equalsIgnoreCase(t)))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove." + t).booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.REMOVE_BASE;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.removeBase(args[2], this.type);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }if ((args[1].equalsIgnoreCase("iVar")) || (args[1].equalsIgnoreCase("infoVariable")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove.ivar").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.REMOVE_WORLD;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.removeInfoVar(args[2], this.type, args[3]);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }if ((args[1].equalsIgnoreCase("w")) || (args[1].equalsIgnoreCase("world")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove.world").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.REMOVE_WORLD;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.removeWorld(args[2], this.type, args[3]);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }if ((args[1].equalsIgnoreCase("wVar")) || (args[1].equalsIgnoreCase("worldVariable")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove.wvar").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.REMOVE_WORLD_VAR;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.removeWorldVar(args[2], this.type, args[3], args[4]);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }
    } else if ((this.type == InfoType.USER) && ((args[0].equalsIgnoreCase("s")) || (args[0].equalsIgnoreCase("set"))))
    {
      if (args.length == 1) {
        MessageUtil.sendMessage(sender, "Usage for '/mchat user set':\n    - /" + this.cmd + " set group <Player> <" + T + ">");

        return true;
      }if ((args[1].equalsIgnoreCase("g")) || (args[1].equalsIgnoreCase("group")))
      {
        if (!MiscUtil.hasCommandPerm(sender, "mchat.user.set.group").booleanValue()) {
          return true;
        }
        InfoEditType editType = InfoEditType.SET_GROUP;

        if (args.length < editType.getLength().intValue()) {
          return editType.sendMsg(sender, this.cmd, this.type).booleanValue();
        }
        Writer.setGroup(args[2], args[3]);
        MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
        return true;
      }
    }

    return false;
  }

  String combineArgs(String[] args, Integer startingPoint) {
    String argString = "";

    for (int i = startingPoint.intValue(); i < args.length; i++) {
      argString = argString + args[i] + " ";
    }
    return argString.trim();
  }
}