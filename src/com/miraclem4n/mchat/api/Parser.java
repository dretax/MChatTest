package com.miraclem4n.mchat.api;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import com.herocraftonline.heroes.util.Messaging;
import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.configs.CensorUtil;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Parser
{
  public static Boolean heroesB;
  public static Heroes heroes;
  public static Boolean townyB;
  public static Boolean mSocialB;

  public static void initialize(MChat instance)
  {
    heroesB = instance.heroesB;
    heroes = instance.heroes;
    townyB = instance.towny;
    mSocialB = instance.mSocial;
  }

  public static String parseMessage(String pName, String world, String msg, String format)
  {
    Object prefix = Reader.getRawPrefix(pName, InfoType.USER, world);
    Object suffix = Reader.getRawSuffix(pName, InfoType.USER, world);
    Object group = Reader.getRawGroup(pName, InfoType.USER, world);

    String vI = ConfigType.MCHAT_VAR_INDICATOR.getString();

    if (msg == null) {
      msg = "";
    }
    if (prefix == null) {
      prefix = "";
    }
    if (suffix == null) {
      suffix = "";
    }
    if (group == null) {
      group = "";
    }

    String hSClass = "";
    String hClass = "";
    String hHealth = "";
    String hHBar = "";
    String hMana = "";
    String hMBar = "";
    String hParty = "";
    String hMastered = "";
    String hLevel = "";
    String hSLevel = "";
    String hExp = "";
    String hSExp = "";
    String hEBar = "";
    String hSEBar = "";

    String tTown = "";
    String tTownName = "";
    String tTitle = "";
    String tSurname = "";
    String tResidentName = "";
    String tPrefix = "";
    String tNamePrefix = "";
    String tPostfix = "";
    String tNamePostfix = "";
    String tNation = "";
    String tNationName = "";
    String tNationTag = "";

    Double locX = Double.valueOf(randomNumber(Integer.valueOf(-100), Integer.valueOf(100)).intValue());
    Double locY = Double.valueOf(randomNumber(Integer.valueOf(-100), Integer.valueOf(100)).intValue());
    Double locZ = Double.valueOf(randomNumber(Integer.valueOf(-100), Integer.valueOf(100)).intValue());

    String loc = "X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ;

    String healthbar = "";
    String health = String.valueOf(randomNumber(Integer.valueOf(1), Integer.valueOf(20)));

    String pWorld = "";

    String hungerLevel = String.valueOf(randomNumber(Integer.valueOf(0), Integer.valueOf(20)));
    String hungerBar = API.createBasicBar(randomNumber(Integer.valueOf(0), Integer.valueOf(20)).intValue(), 20.0F, 10.0F);
    String level = String.valueOf(randomNumber(Integer.valueOf(1), Integer.valueOf(2)));
    String exp = String.valueOf(randomNumber(Integer.valueOf(0), Integer.valueOf(200))) + "/" + (randomNumber(Integer.valueOf(1), Integer.valueOf(2)).intValue() + 1) * 10;
    String expBar = API.createBasicBar(randomNumber(Integer.valueOf(0), Integer.valueOf(200)).intValue(), (randomNumber(Integer.valueOf(1), Integer.valueOf(2)).intValue() + 1) * 10, 10.0F);
    String tExp = String.valueOf(randomNumber(Integer.valueOf(0), Integer.valueOf(300)));
    String gMode = String.valueOf(randomNumber(Integer.valueOf(0), Integer.valueOf(1)));

    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat(LocaleType.FORMAT_DATE.getRaw());
    String time = dateFormat.format(now);

    String dName = pName;

    String dType = "";

    if ((mSocialB.booleanValue()) && (MChat.shouting.get(pName) != null) && (((Boolean)MChat.shouting.get(pName)).booleanValue()))
    {
      dType = MChat.shoutFormat;
    } else if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble().doubleValue() > 0.0D) {
      dType = LocaleType.FORMAT_LOCAL.getVal();
    }

    String sType = "";

    if ((MChat.spying.get(pName) != null) && (((Boolean)MChat.spying.get(pName)).booleanValue()))
    {
      sType = LocaleType.FORMAT_SPY.getVal();
    }

    if (Bukkit.getServer().getPlayer(pName) != null) {
      Player player = Bukkit.getServer().getPlayer(pName);

      locX = Double.valueOf(player.getLocation().getX());
      locY = Double.valueOf(player.getLocation().getY());
      locZ = Double.valueOf(player.getLocation().getZ());

      loc = "X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ;

      healthbar = API.createHealthBar(player);
      health = String.valueOf(player.getHealth());

      pWorld = player.getWorld().getName();

      hungerLevel = String.valueOf(player.getFoodLevel());
      hungerBar = API.createBasicBar(player.getFoodLevel(), 20.0F, 10.0F);
      level = String.valueOf(player.getLevel());
      exp = String.valueOf(player.getExp()) + "/" + (player.getLevel() + 1) * 10;
      expBar = API.createBasicBar(player.getExp(), (player.getLevel() + 1) * 10, 10.0F);
      tExp = String.valueOf(player.getTotalExperience());
      gMode = "";

      if ((player.getGameMode() != null) && (player.getGameMode().name() != null)) {
        gMode = player.getGameMode().name();
      }

      dName = player.getDisplayName();

      if (heroesB.booleanValue()) {
        Hero hero = heroes.getCharacterManager().getHero(player);
        HeroClass heroClass = hero.getHeroClass();
        HeroClass heroSClass = hero.getSecondClass();

        int hL = hero.getLevel();
        int hSL = hero.getLevel(heroSClass);
        double hE = hero.getExperience(heroClass);
        double hSE = hero.getExperience(heroSClass);

        hClass = hero.getHeroClass().getName();
        hHealth = String.valueOf(hero.getHealth());
        hHBar = Messaging.createHealthBar(hero.getHealth(), hero.getMaxHealth());
        hMana = String.valueOf(hero.getMana());
        hLevel = String.valueOf(hL);
        hExp = String.valueOf(hE);
        hEBar = Messaging.createExperienceBar(hero, heroClass);

        Integer hMMana = Integer.valueOf(hero.getMaxMana());

        if (hMMana != null) {
          hMBar = Messaging.createManaBar(hero.getMana(), hero.getMaxMana());
        }
        if (hero.getParty() != null) {
          hParty = hero.getParty().toString();
        }
        if (heroSClass != null) {
          hSClass = heroSClass.getName();
          hSLevel = String.valueOf(hSL);
          hSExp = String.valueOf(hSE);
          hSEBar = Messaging.createExperienceBar(hero, heroSClass);
        }

        if ((hero.isMaster(heroClass)) && ((heroSClass == null) || (hero.isMaster(heroSClass))))
        {
          hMastered = LocaleType.MESSAGE_HEROES_TRUE.getVal();
        }
        else hMastered = LocaleType.MESSAGE_HEROES_FALSE.getVal();
      }

      if (townyB.booleanValue())
        try {
          Resident resident = TownyUniverse.getDataSource().getResident(pName);

          if (resident.hasTown()) {
            Town town = resident.getTown();

            tTown = town.getName();
            tTownName = TownyFormatter.getFormattedTownName(town);

            tTitle = resident.getTitle();
            tSurname = resident.getSurname();
            tResidentName = resident.getFormattedName();

            tPrefix = resident.hasTitle() ? resident.getTitle() : TownyFormatter.getNamePrefix(resident);
            tNamePrefix = TownyFormatter.getNamePrefix(resident);
            tPostfix = resident.hasSurname() ? resident.getSurname() : TownyFormatter.getNamePostfix(resident);
            tNamePostfix = TownyFormatter.getNamePostfix(resident);

            if (resident.hasNation()) {
              Nation nation = town.getNation();

              tNation = nation.getName();
              tNationName = nation.getFormattedName();
              tNationTag = nation.getTag();
            }
          }
        }
        catch (Exception ignored) {
        }
    }
    String formatAll = parseVars(format, pName, world);

    msg = msg.replaceAll("%", "%%");

    formatAll = formatAll.replaceAll("%", "%%");

    formatAll = MessageUtil.addColour(formatAll);

    if ((!API.checkPermissions(pName, world, "mchat.bypass.clock").booleanValue()) && (ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger().intValue() > 0))
    {
      msg = fixCaps(msg, ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger());
    }
    if (formatAll == null) {
      return msg;
    }
    if (API.checkPermissions(pName, world, "mchat.coloredchat").booleanValue()) {
      msg = MessageUtil.addColour(msg);
    }
    if (!API.checkPermissions(pName, world, "mchat.censorbypass").booleanValue()) {
      msg = replaceCensoredWords(msg);
    }

    TreeMap fVarMap = new TreeMap();
    TreeMap rVarMap = new TreeMap();
    TreeMap lVarMap = new TreeMap();

    addVar(fVarMap, vI + "mnameformat," + vI + "mnf", LocaleType.FORMAT_NAME.getVal());
    addVar(fVarMap, vI + "healthbar," + vI + "hb", healthbar);

    addVar(rVarMap, vI + "distancetype," + vI + "dtype", dType);
    addVar(rVarMap, vI + "displayname," + vI + "dname," + vI + "dn", dName);
    addVar(rVarMap, vI + "experiencebar," + vI + "expb," + vI + "ebar," + vI + "eb", expBar);
    addVar(rVarMap, vI + "experience," + vI + "exp", exp);
    addVar(rVarMap, vI + "gamemode," + vI + "gm", gMode);
    addVar(rVarMap, vI + "group," + vI + "g", group);
    addVar(rVarMap, vI + "hungerbar," + vI + "hub", hungerBar);
    addVar(rVarMap, vI + "hunger", hungerLevel);
    addVar(rVarMap, vI + "health," + vI + "h", health);
    addVar(rVarMap, vI + "location," + vI + "loc", loc);
    addVar(rVarMap, vI + "level," + vI + "l", level);
    addVar(rVarMap, vI + "mname," + vI + "mn", Reader.getMName(pName));
    addVar(rVarMap, vI + "pname," + vI + "n", pName);
    addVar(rVarMap, vI + "prefix," + vI + "p", prefix);
    addVar(rVarMap, vI + "spying," + vI + "spy", sType);
    addVar(rVarMap, vI + "suffix," + vI + "s", suffix);
    addVar(rVarMap, vI + "totalexp," + vI + "texp," + vI + "te", tExp);
    addVar(rVarMap, vI + "time," + vI + "t", time);
    addVar(rVarMap, vI + "world," + vI + "w", pWorld);
    addVar(rVarMap, vI + "Groupname," + vI + "Gname," + vI + "G", Reader.getGroupName(group.toString()));
    addVar(rVarMap, vI + "HClass," + vI + "HC", hClass);
    addVar(rVarMap, vI + "HExp," + vI + "HEx", hExp);
    addVar(rVarMap, vI + "HEBar," + vI + "HEb", hEBar);
    addVar(rVarMap, vI + "HHBar," + vI + "HHB", hHBar);
    addVar(rVarMap, vI + "HHealth," + vI + "HH", hHealth);
    addVar(rVarMap, vI + "HLevel," + vI + "HL", hLevel);
    addVar(rVarMap, vI + "HMastered," + vI + "HMa", hMastered);
    addVar(rVarMap, vI + "HMana," + vI + "HMn", hMana);
    addVar(rVarMap, vI + "HMBar," + vI + "HMb", hMBar);
    addVar(rVarMap, vI + "HParty," + vI + "HPa", hParty);
    addVar(rVarMap, vI + "HSecClass," + vI + "HSC", hSClass);
    addVar(rVarMap, vI + "HSecExp," + vI + "HSEx", hSExp);
    addVar(rVarMap, vI + "HSecEBar," + vI + "HSEb", hSEBar);
    addVar(rVarMap, vI + "HSecLevel," + vI + "HSL", hSLevel);

    addVar(rVarMap, vI + "town", tTown);
    addVar(rVarMap, vI + "townname", tTownName);
    addVar(rVarMap, vI + "townysurname", tSurname);
    addVar(rVarMap, vI + "townytitle", tTitle);
    addVar(rVarMap, vI + "townyresidentname", tResidentName);
    addVar(rVarMap, vI + "townyprefix", tPrefix);
    addVar(rVarMap, vI + "townynameprefix", tNamePrefix);
    addVar(rVarMap, vI + "townypostfix", tPostfix);
    addVar(rVarMap, vI + "townynamepostfix", tNamePostfix);
    addVar(rVarMap, vI + "townynation", tNation);
    addVar(rVarMap, vI + "townynationname", tNationName);
    addVar(rVarMap, vI + "townynationtag", tNationTag);

    addVar(rVarMap, vI + "Worldname," + vI + "Wname," + vI + "W", Reader.getWorldName(pWorld));

    addVar(lVarMap, vI + "message," + vI + "msg," + vI + "m", msg);

    formatAll = replaceCustVars(pName, formatAll);

    formatAll = replaceVars(formatAll, fVarMap.descendingMap(), Boolean.valueOf(true));
    formatAll = replaceVars(formatAll, rVarMap.descendingMap(), Boolean.valueOf(true));
    formatAll = replaceVars(formatAll, lVarMap.descendingMap(), Boolean.valueOf(false));

    return formatAll;
  }

  public static String parseChatMessage(String pName, String world, String msg)
  {
    return parseMessage(pName, world, msg, ConfigType.FORMAT_CHAT.getString());
  }

  public static String parsePlayerName(String pName, String world)
  {
    return parseMessage(pName, world, "", LocaleType.FORMAT_NAME.getRaw());
  }

  public static String parseEvent(String pName, String world, EventType type)
  {
    return parseMessage(pName, world, "", API.replace(Reader.getEventMessage(type), "player", parsePlayerName(pName, world), IndicatorType.LOCALE_VAR));
  }

  public static String parseTabbedList(String pName, String world)
  {
    return parseMessage(pName, world, "", LocaleType.FORMAT_TABBED_LIST.getRaw());
  }

  public static String parseListCmd(String pName, String world)
  {
    return parseMessage(pName, world, "", LocaleType.FORMAT_LIST_CMD.getRaw());
  }

  public static String parseMe(String pName, String world, String msg)
  {
    return parseMessage(pName, world, msg, LocaleType.FORMAT_ME.getRaw());
  }

  private static TreeMap<String, Object> addVar(TreeMap<String, Object> map, String keys, Object value)
  {
    if (keys.contains(","))
      for (String s : keys.split(","))
        if ((s != null) && (value != null))
        {
          map.put(s, value);
        }
    else if (value != null) {
      map.put(keys, value);
    }
    return map;
  }

  private static String fixCaps(String format, Integer range) {
    if (range.intValue() < 1) {
      return format;
    }
    Pattern pattern = Pattern.compile("([A-Z]{" + range + ",300})");
    Matcher matcher = pattern.matcher(format);
    StringBuffer sb = new StringBuffer();

    while (matcher.find()) {
      matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group().toLowerCase()));
    }
    matcher.appendTail(sb);

    format = sb.toString();

    return format;
  }

  private static String parseVars(String format, String pName, String world) {
    String vI = "\\" + ConfigType.MCHAT_VAR_INDICATOR.getString();
    Pattern pattern = Pattern.compile(vI + "<(.*?)>");
    Matcher matcher = pattern.matcher(format);
    StringBuffer sb = new StringBuffer();

    while (matcher.find()) {
      String var = Reader.getRawInfo(pName, InfoType.USER, world, matcher.group(1)).toString();
      matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
    }

    matcher.appendTail(sb);

    return sb.toString();
  }

  private static String replaceVars(String format, Map<String, Object> map, Boolean doColour) {
    for (Map.Entry entry : map.entrySet()) {
      String value = entry.getValue().toString();

      if (doColour.booleanValue()) {
        value = MessageUtil.addColour(value);
      }
      format = format.replace((CharSequence)entry.getKey(), value);
    }

    return format;
  }

  private static String replaceCustVars(String pName, String format) {
    Set<Entry<String, String>> varSet = API.varMap.entrySet();

    for (Map.Entry entry : varSet) {
      String pKey = IndicatorType.CUS_VAR.getValue() + ((String)entry.getKey()).replace(new StringBuilder().append(pName).append("|").toString(), "");
      String value = (String)entry.getValue();

      if (format.contains(pKey)) {
        format = format.replace(pKey, MessageUtil.addColour(value));
      }
    }
    for (Map.Entry entry : varSet) {
      String gKey = IndicatorType.CUS_VAR.getValue() + ((String)entry.getKey()).replace("%^global^%|", "");
      String value = (String)entry.getValue();

      if (format.contains(gKey)) {
        format = format.replace(gKey, MessageUtil.addColour(value));
      }
    }
    return format;
  }

  private static String replaceCensoredWords(String msg) {
    if (ConfigType.MCHAT_IP_CENSOR.getBoolean().booleanValue()) {
      msg = replacer(msg, "([0-9]{1,3}\\.){3}([0-9]{1,3})", "*.*.*.*");
    }
    for (Map.Entry entry : CensorUtil.getConfig().getValues(false).entrySet()) {
      String val = entry.getValue().toString();

      msg = replacer(msg, "(?i)" + (String)entry.getKey(), val);
    }

    return msg;
  }

  private static String replacer(String msg, String regex, String replacement) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(msg);
    StringBuffer sb = new StringBuffer();

    while (matcher.find()) {
      matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
    }
    matcher.appendTail(sb);

    msg = sb.toString();

    return msg;
  }

  private static Integer randomNumber(Integer minValue, Integer maxValue) {
    Random random = new Random();

    return Integer.valueOf(random.nextInt(maxValue.intValue() - minValue.intValue() + 1) + minValue.intValue());
  }
}