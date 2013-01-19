package com.miraclem4n.mchat.types;

import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.command.CommandSender;

public enum InfoEditType
{
  ADD_INFO_VAR(Integer.valueOf(5), "/%cmd add ivar <%Type> <Variable> [Value]"), 
  ADD_BASE(Integer.valueOf(3), "/%cmd add %type <%Type>"), 
  ADD_WORLD(Integer.valueOf(4), "/%cmd add world <%Type> <World>"), 
  ADD_WORLD_VAR(Integer.valueOf(6), "/%cmd add wvar <%Type> <World> <Variable> [Value]"), 
  REMOVE_INFO_VAR(Integer.valueOf(4), "/%cmd remove ivar <%Type> <Variable>"), 
  REMOVE_BASE(Integer.valueOf(3), "/%cmd remove %type <%Type>"), 
  REMOVE_WORLD(Integer.valueOf(4), "/%cmd remove world <%Type> <World>"), 
  REMOVE_WORLD_VAR(Integer.valueOf(5), "/%cmd remove wvar <%Type> <World> <Variable>"), 
  SET_GROUP(Integer.valueOf(4), "/%cmd set group <Player> <Group>");

  Integer i;
  String msg;

  private InfoEditType(Integer i, String msg) { this.i = i;
    this.msg = msg; }

  public Integer getLength()
  {
    return this.i;
  }

  public Boolean sendMsg(CommandSender sender, String cmd, InfoType type) {
    String t = "player";
    String T = "Player";

    if (type == InfoType.GROUP) {
      t = "group";
      T = "Group";
    }

    MessageUtil.sendMessage(sender, this.msg.replace("%cmd", cmd).replace("%Type", T).replace("%type", t));
    return Boolean.valueOf(true);
  }
}