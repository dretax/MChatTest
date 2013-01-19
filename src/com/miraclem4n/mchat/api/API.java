package com.miraclem4n.mchat.api;

import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import net.milkbowl.vault.permission.Permission;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class API
{
  static Permission vPerm;
  static Boolean vaultB;
  static WorldsHolder gmWH;
  static Boolean gmB;
  static PermissionManager pexPermissions;
  static Boolean pexB;
  static Boolean bPermB;
  static Boolean pBukkitB;
  static TreeMap<String, String> varMap;

  public static void initialize()
  {
    setupPlugins();

    varMap = new TreeMap();
  }

  public static void addGlobalVar(String var, String value)
  {
    if ((var == null) || (var.isEmpty())) {
      return;
    }
    if (value == null) {
      value = "";
    }
    varMap.put(new StringBuilder().append("%^global^%|").append(var).toString(), value);
  }

  public static void addPlayerVar(String pName, String var, String value)
  {
    if ((var == null) || (var.isEmpty())) {
      return;
    }
    if (value == null) {
      value = "";
    }
    varMap.put(new StringBuilder().append(pName).append("|").append(var).toString(), value);
  }

  public static String createHealthBar(Player player)
  {
    float maxHealth = 20.0F;
    float barLength = 10.0F;
    float health = player.getHealth();

    return createBasicBar(health, maxHealth, barLength);
  }

  public static String createFoodBar(Player player)
  {
    float maxFood = 20.0F;
    float barLength = 10.0F;
    float food = player.getFoodLevel();

    return createBasicBar(food, maxFood, barLength);
  }

  public static String createBasicBar(float currentValue, float maxValue, float barLength)
  {
    int fill = Math.round(currentValue / maxValue * barLength);

    String barColor = fill <= barLength / 7.0F ? "&e" : fill <= barLength / 4.0F ? "&4" : "&2";

    StringBuilder out = new StringBuilder();
    out.append(barColor);

    for (int i = 0; i < barLength; i++) {
      if (i == fill) {
        out.append("&8");
      }
      out.append("|");
    }

    out.append("&f");

    return out.toString();
  }

  public static Boolean checkPermissions(Player player, World world, String node)
  {
    return Boolean.valueOf((checkPermissions(player.getName(), world.getName(), node).booleanValue()) || (player.hasPermission(node)) || (player.isOp()));
  }

  public static Boolean checkPermissions(String pName, String world, String node)
  {
    if ((vaultB.booleanValue()) && 
      (vPerm.has(world, pName, node))) {
      return Boolean.valueOf(true);
    }
    if ((gmB.booleanValue()) && 
      (gmWH.getWorldPermissions(pName).getPermissionBoolean(pName, node))) {
      return Boolean.valueOf(true);
    }
    if ((pexB.booleanValue()) && 
      (pexPermissions.has(pName, world, node))) {
      return Boolean.valueOf(true);
    }
    if ((Bukkit.getServer().getPlayer(pName) != null) && 
      (Bukkit.getServer().getPlayer(pName).hasPermission(node))) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }

  public static Boolean checkPermissions(CommandSender sender, String node)
  {
    if ((vaultB.booleanValue()) && 
      (vPerm.has(sender, node))) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(sender.hasPermission(node));
  }

  public static String replace(String source, TreeMap<String, String> changes, IndicatorType type)
  {
    NavigableMap<String, String> changed = changes.descendingMap();

    for (Map.Entry entry : changed.entrySet()) {
      source = source.replace(new StringBuilder().append(type.getValue()).append((String)entry.getKey()).toString(), (CharSequence)entry.getValue());
    }
    return source;
  }

  public static String replace(String source, String search, String replace, IndicatorType type)
  {
    return source.replace(new StringBuilder().append(type.getValue()).append(search).toString(), replace);
  }

  private static void setupPlugins() {
    PluginManager pm = Bukkit.getServer().getPluginManager();

    Plugin pluginTest = pm.getPlugin("Vault");
    vaultB = Boolean.valueOf(pluginTest != null);
    if (vaultB.booleanValue()) {
      MessageUtil.log(new StringBuilder().append("[MChat] <Plugin> ").append(pluginTest.getDescription().getName()).append(" v").append(pluginTest.getDescription().getVersion()).append(" hooked!.").toString());

      RegisteredServiceProvider permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

      if (permissionProvider != null) {
        vPerm = (Permission)permissionProvider.getProvider();
      }
      vaultB = Boolean.valueOf(vPerm != null);
    }

    pluginTest = pm.getPlugin("PermissionsBukkit");
    pBukkitB = Boolean.valueOf(pluginTest != null);
    if (pBukkitB.booleanValue()) {
      MessageUtil.log(new StringBuilder().append("[MChat] <Permissions> ").append(pluginTest.getDescription().getName()).append(" v").append(pluginTest.getDescription().getVersion()).append(" hooked!.").toString());
    }
    pluginTest = pm.getPlugin("bPermissions");
    bPermB = Boolean.valueOf(pluginTest != null);
    if (bPermB.booleanValue()) {
      MessageUtil.log(new StringBuilder().append("[MChat] <Permissions> ").append(pluginTest.getDescription().getName()).append(" v").append(pluginTest.getDescription().getVersion()).append(" hooked!.").toString());
    }
    pluginTest = pm.getPlugin("PermissionsEx");
    pexB = Boolean.valueOf(pluginTest != null);
    if (pexB.booleanValue()) {
      pexPermissions = PermissionsEx.getPermissionManager();
      MessageUtil.log(new StringBuilder().append("[MChat] <Permissions> ").append(pluginTest.getDescription().getName()).append(" v").append(pluginTest.getDescription().getVersion()).append(" hooked!.").toString());
    }

    pluginTest = pm.getPlugin("GroupManager");
    gmB = Boolean.valueOf(pluginTest != null);
    if (gmB.booleanValue()) {
      gmWH = ((GroupManager)pluginTest).getWorldsHolder();
      MessageUtil.log(new StringBuilder().append("[MChat] <Permissions> ").append(pluginTest.getDescription().getName()).append(" v").append(pluginTest.getDescription().getVersion()).append(" hooked!.").toString());
    }

    if ((!vaultB.booleanValue()) && (!pBukkitB.booleanValue()) && (!bPermB.booleanValue()) && (!pexB.booleanValue()) && (!gmB.booleanValue()))
      MessageUtil.log("[MChat] <Permissions> SuperPerms hooked!.");
  }
}