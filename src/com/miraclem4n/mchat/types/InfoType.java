package com.miraclem4n.mchat.types;

public enum InfoType
{
  GROUP("groups"), 
  USER("users");

  private final String name;

  private InfoType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static InfoType fromName(String name) {
    for (InfoType type : values()) {
      if (type.name.contains(name))
        return type;
    }
    return null;
  }
}