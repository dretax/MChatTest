package com.miraclem4n.mchat.types;

import com.miraclem4n.mchat.types.config.ConfigType;

public enum IndicatorType
{
  MISC_VAR(ConfigType.MCHAT_VAR_INDICATOR), 
  CUS_VAR(ConfigType.MCHAT_CUS_VAR_INDICATOR), 
  LOCALE_VAR(ConfigType.MCHAT_LOCALE_VAR_INDICATOR);

  private ConfigType type;

  private IndicatorType(ConfigType type) {
    this.type = type;
  }

  public String getValue() {
    return this.type.getString();
  }
}