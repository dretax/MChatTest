package com.miraclem4n.mchat.util;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.CommandSender;

public class MessageUtil
{
  public static void sendRawMessage(CommandSender sender, String message)
  {
    sender.sendMessage(message);
  }

  public static void sendColouredMessage(CommandSender sender, String message) {
    sender.sendMessage(addColour(message));
  }

  public static void sendMessage(CommandSender sender, String message) {
    sender.sendMessage(format(message));
  }

  public static void log(Object message)
  {
    System.out.println(message);
  }

  public static void log(Level level, Object message)
  {
    Logger.getLogger("Minecraft").log(level, message.toString());
  }

  public static void logFormatted(Object message)
  {
    System.out.println(format(message.toString()));
  }

  public static String addColour(String string)
  {
    string = string.replace("`e", "").replace("`r", "§c").replace("`R", "§4").replace("`y", "§e").replace("`Y", "§6").replace("`g", "§a").replace("`G", "§2").replace("`a", "§b").replace("`A", "§3").replace("`b", "§9").replace("`B", "§1").replace("`p", "§d").replace("`P", "§5").replace("`k", "§0").replace("`s", "§7").replace("`S", "§8").replace("`w", "§f");

    string = string.replace("<r>", "").replace("<black>", "§0").replace("<navy>", "§1").replace("<green>", "§2").replace("<teal>", "§3").replace("<red>", "§4").replace("<purple>", "§5").replace("<gold>", "§6").replace("<silver>", "§7").replace("<gray>", "§8").replace("<blue>", "§9").replace("<lime>", "§a").replace("<aqua>", "§b").replace("<rose>", "§c").replace("<pink>", "§d").replace("<yellow>", "§e").replace("<white>", "§f");

    string = string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "§$2");

    string = string.replaceAll("(&([a-fk-orA-FK-OR0-9]))", "§$2");

    return string.replace("&&", "&");
  }

  public static String removeColour(String string)
  {
    string = addColour(string);

    return string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "§z");
  }

  public static String format(String message)
  {
    return addColour("&2[&4M&8Chat&2] &6" + message);
  }

  public static String format(String name, String message)
  {
    return addColour("&2[&4" + name + "&2] &6" + message);
  }
}