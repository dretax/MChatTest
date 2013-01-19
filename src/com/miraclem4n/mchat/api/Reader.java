package com.miraclem4n.mchat.api;

import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.anjocaido.groupmanager.data.GroupVariables;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.data.UserVariables;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;

public class Reader
{
  public static Object getRawInfo(String name, InfoType type, String world, String info)
  {
    if (type == null) {
      type = InfoType.USER;
    }
    if (world == null) {
      world = ((World)Bukkit.getServer().getWorlds().get(0)).getName();
    }
    if (info == null) {
      info = "prefix";
    }
    if (ConfigType.INFO_USE_LEVELED_NODES.getBoolean().booleanValue()) {
      return getLeveledInfo(name, world, info);
    }
    if (ConfigType.INFO_USE_OLD_NODES.getBoolean().booleanValue()) {
      return getBukkitInfo(name, world, info);
    }
    if (ConfigType.INFO_USE_NEW_INFO.getBoolean().booleanValue()) {
      return getMChatInfo(name, type, world, info);
    }
    if (API.gmB.booleanValue()) {
      return getGroupManagerInfo(name, type, world, info);
    }
    if (API.pexB.booleanValue()) {
      return getPEXInfo(name, type, world, info);
    }
    if (API.bPermB.booleanValue()) {
      return getbPermInfo(name, type, world, info);
    }
    return getMChatInfo(name, type, world, info);
  }

  public static Object getRawPrefix(String name, InfoType type, String world)
  {
    return getRawInfo(name, type, world, "prefix");
  }

  public static Object getRawSuffix(String name, InfoType type, String world)
  {
    return getRawInfo(name, type, world, "suffix");
  }

  public static Object getRawGroup(String name, InfoType type, String world)
  {
    return getRawInfo(name, type, world, "group");
  }

  public static String getInfo(String name, InfoType type, String world, String info)
  {
    return MessageUtil.addColour(getRawInfo(name, type, world, info).toString());
  }

  public static String getPrefix(String name, InfoType type, String world)
  {
    return getInfo(name, type, world, "prefix");
  }

  public static String getSuffix(String name, InfoType type, String world)
  {
    return getInfo(name, type, world, "suffix");
  }

  public static String getGroup(String name, String world)
  {
    return getInfo(name, InfoType.USER, world, "group");
  }

  private static Object getMChatInfo(String name, InfoType type, String world, String info)
  {
    if (info.equals("group")) {
      return getMChatGroup(name);
    }
    String iType = type.getName();

    if (InfoUtil.getConfig().isSet(iType + "." + name + ".info." + info)) {
      return InfoUtil.getConfig().get(iType + "." + name + ".info." + info);
    }
    if (InfoUtil.getConfig().isSet(iType + "." + name + ".worlds." + world + "." + info)) {
      return InfoUtil.getConfig().get(iType + "." + name + ".worlds." + world + "." + info);
    }
    return "";
  }

  private static Object getMChatGroup(String name) {
    if (InfoUtil.getConfig().isSet("users." + name + ".group")) {
      return InfoUtil.getConfig().get("users." + name + ".group");
    }
    return "";
  }

  private static Object getLeveledInfo(String name, String world, String info)
  {
    HashMap iMap = new HashMap();

    if ((API.pBukkitB.booleanValue()) && 
      (info.equals("group"))) {
      return getPermBukkitGroup(name);
    }
    if (!InfoUtil.getConfig().isSet("mchat." + info)) {
      return "";
    }
    if (!InfoUtil.getConfig().isSet("rank." + info)) {
      return getBukkitInfo(name, world, info);
    }
    for (Map.Entry entry : InfoUtil.getConfig().getValues(true).entrySet())
      if ((((String)entry.getKey()).contains("mchat." + info + ".")) && 
        (API.checkPermissions(name, world, (String)entry.getKey()).booleanValue())) {
        String rVal = ((String)entry.getKey()).replaceFirst("mchat\\.", "rank.");

        if (InfoUtil.getConfig().isSet(rVal))
        {
          try
          {
            iMap.put(Integer.valueOf(InfoUtil.getConfig().getInt(rVal)), entry.getValue().toString());
          } catch (NumberFormatException ignored) {
          }
        }
      }
    for (int i = 0; i < 101; i++) {
      if ((iMap.get(Integer.valueOf(i)) != null) && (!((String)iMap.get(Integer.valueOf(i))).isEmpty())) {
        return iMap.get(Integer.valueOf(i));
      }
    }
    return getBukkitInfo(name, world, info);
  }

  private static Object getBukkitInfo(String name, String world, String info)
  {
    if ((API.pBukkitB.booleanValue()) && 
      (info.equals("group"))) {
      return getPermBukkitGroup(name);
    }
    if (!InfoUtil.getConfig().isSet("mchat." + info)) {
      return "";
    }
    for (Map.Entry entry : InfoUtil.getConfig().getValues(true).entrySet()) {
      if ((((String)entry.getKey()).contains("mchat." + info + ".")) && 
        (API.checkPermissions(name, world, (String)entry.getKey()).booleanValue())) {
        Object infoResolve = entry.getValue();

        if ((infoResolve == null) || (info.isEmpty())) break;
        return infoResolve;
      }

    }

    return "";
  }

  private static String getPermBukkitGroup(String name) {
    PermissionsPlugin pBukkit = (PermissionsPlugin)Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit");

    List pGroups = pBukkit.getGroups(name);
    try
    {
      return ((com.platymuus.bukkit.permissions.Group)pGroups.get(0)).getName(); } catch (Exception ignored) {
    }
    return "";
  }

  private static Object getGroupManagerInfo(String name, InfoType type, String world, String info)
  {
    OverloadedWorldHolder gmPermissions = API.gmWH.getWorldData(world);

    if (info.equals("group")) {
      return getGroupManagerGroup(name, world);
    }
    String infoString = "";

    if (type == InfoType.USER) {
      infoString = gmPermissions.getUser(name).getVariables().getVarString(info);
    }
    if (type == InfoType.GROUP) {
      infoString = gmPermissions.getGroup(name).getVariables().getVarString(info);
    }
    return infoString;
  }

  private static String getGroupManagerGroup(String name, String world) {
    OverloadedWorldHolder gmPermissions = API.gmWH.getWorldData(world);

    String group = gmPermissions.getUser(name).getGroup().getName();

    if (group == null) {
      return "";
    }
    return group;
  }

  private static Object getPEXInfo(String name, InfoType type, String world, String info)
  {
    Object infoString = "";

    if ((name.isEmpty()) || (name == null)) {
      return infoString;
    }
    if (info.equals("group")) {
      return getPEXGroup(name);
    }
    if (type == InfoType.USER) {
      PermissionUser user = API.pexPermissions.getUser(name);

      if (info.equals("prefix")) {
        infoString = user.getPrefix(world);
      }
      else if (info.equals("suffix")) {
        infoString = user.getSuffix(world);
      }
      else
        infoString = user.getOption(info, world);
    } else if (type == InfoType.GROUP) {
      PermissionGroup group = API.pexPermissions.getGroup(name);

      if (info.equals("prefix")) {
        infoString = group.getPrefix(world);
      }
      else if (info.equals("suffix")) {
        infoString = group.getSuffix(world);
      }
      else {
        infoString = group.getOption(info, world);
      }
    }
    return infoString;
  }

  private static Object getPEXGroup(String name) {
    String[] groupNames = API.pexPermissions.getUser(name).getGroupsNames();
    String group = "";

    if (groupNames.length > 0) {
      group = API.pexPermissions.getUser(name).getGroupsNames()[0];
    }
    return group;
  }

  private static Object getbPermInfo(String name, InfoType type, String world, String info)
  {
    if (info.equals("group")) {
      return getbPermGroup(name, world);
    }
    Object userString = "";

    if (type == InfoType.USER) {
      userString = ApiLayer.getValue(world, CalculableType.USER, name, info);
    }
    else if (type == InfoType.GROUP) {
      userString = ApiLayer.getValue(world, CalculableType.GROUP, name, info);
    }
    return userString;
  }

  private static Object getbPermGroup(String name, String world) {
    String[] groupNames = ApiLayer.getGroups(world, CalculableType.USER, name);
    String group = "";

    if (groupNames.length > 0) {
      group = ApiLayer.getGroups(world, CalculableType.USER, name)[0];
    }
    return group;
  }

  public static String getGroupName(String group)
  {
    if (group.isEmpty()) {
      return "";
    }
    if (InfoUtil.getConfig().isSet("groupnames." + group)) {
      return InfoUtil.getConfig().getString("groupnames." + group);
    }
    return group;
  }

  public static String getWorldName(String world)
  {
    if (world.isEmpty()) {
      return "";
    }
    if (InfoUtil.getConfig().isSet("worldnames." + world)) {
      return InfoUtil.getConfig().getString("worldnames." + world);
    }
    return world;
  }

  public static String getMName(String name)
  {
    if ((InfoUtil.getConfig().isSet("mname." + name)) && 
      (!InfoUtil.getConfig().getString("mname." + name).isEmpty())) {
      return InfoUtil.getConfig().getString("mname." + name);
    }
    return name;
  }

  public static String getEventMessage(EventType type)
  {
    if (type.getName().equalsIgnoreCase("join")) {
      return LocaleType.MESSAGE_EVENT_JOIN.getRaw();
    }
    if (type.getName().equalsIgnoreCase("kick")) {
      return LocaleType.MESSAGE_EVENT_KICK.getRaw();
    }
    if (type.getName().equalsIgnoreCase("leave")) {
      return LocaleType.MESSAGE_EVENT_LEAVE.getRaw();
    }
    return "";
  }
}