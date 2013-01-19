package com.miraclem4n.mchat.types.config;

import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.util.MessageUtil;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.configuration.file.YamlConfiguration;

public enum ConfigType
{
  FORMAT_CHAT("format.chat"), 

  MCHAT_API_ONLY("mchat.apiOnly"), 
  MCHAT_ALTER_EVENTS("mchat.alter.events"), 
  MCHAT_ALTER_DEATH("mchat.alter.death"), 
  MCHAT_CHAT_DISTANCE("mchat.chatDistance"), 
  MCHAT_VAR_INDICATOR("mchat.varIndicator"), 
  MCHAT_LOCALE_VAR_INDICATOR("mchat.localeVarIndicator"), 
  MCHAT_CUS_VAR_INDICATOR("mchat.cusVarIndicator"), 
  MCHAT_IP_CENSOR("mchat.IPCensor"), 
  MCHAT_CAPS_LOCK_RANGE("mchat.cLockRange"), 

  SUPPRESS_USE_DEATH("suppress.useDeath"), 
  SUPPRESS_USE_JOIN("suppress.useJoin"), 
  SUPPRESS_USE_KICK("suppress.useKick"), 
  SUPPRESS_USE_QUIT("suppress.useQuit"), 
  SUPPRESS_MAX_DEATH("suppress.maxDeath"), 
  SUPPRESS_MAX_JOIN("suppress.maxJoin"), 
  SUPPRESS_MAX_KICK("suppress.maxKick"), 
  SUPPRESS_MAX_QUIT("suppress.maxQuit"), 

  INFO_USE_NEW_INFO("info.useNewInfo"), 
  INFO_USE_LEVELED_NODES("info.useLeveledNodes"), 
  INFO_USE_OLD_NODES("info.useOldNodes"), 
  INFO_ADD_NEW_PLAYERS("info.addNewPlayers"), 
  INFO_DEFAULT_GROUP("info.defaultGroup"), 

  ALIASES_ME("aliases.mchatme");

  private final String option;

  private ConfigType(String option) {
    this.option = option;
  }

  private Object getObject() {
    Object value = ConfigUtil.getConfig().get(this.option);

    if ((value instanceof String)) {
      String val = (String)value;

      value = MessageUtil.addColour(val);
    }

    return value;
  }

  public Boolean getBoolean() {
    Object object = getObject();

    return Boolean.valueOf((object instanceof Boolean) ? ((Boolean)object).booleanValue() : false);
  }

  public String getString() {
    Object object = getObject();

    return object != null ? object.toString() : "";
  }

  public Integer getInteger() {
    Object object = getObject();

    return Integer.valueOf((object instanceof Number) ? ((Integer)object).intValue() : 0);
  }

  public Double getDouble() {
    Object object = getObject();

    return Double.valueOf((object instanceof Number) ? ((Double)object).doubleValue() : 0.0D);
  }

  public ArrayList<String> getList() {
    Object object = getObject();
    ArrayList list = new ArrayList();
    Iterator i$;
    if ((object instanceof ArrayList)) {
      ArrayList aList = (ArrayList)object;

      for (i$ = aList.iterator(); i$.hasNext(); ) { Object obj = i$.next();
        list.add((String)obj);
      }
    }

    return list;
  }
}