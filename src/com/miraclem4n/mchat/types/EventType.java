package com.miraclem4n.mchat.types;

public enum EventType
{
  JOIN("join"), 
  QUIT("leave"), 
  LEAVE("leave"), 
  KICK("kick");

  private final String name;

  private EventType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static EventType fromName(String name) {
    for (EventType type : values()) {
      if (type.name.contains(name))
        return type;
    }
    return null;
  }
}