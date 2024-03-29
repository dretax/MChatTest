package in.mDev.MiracleM4n.mChatSuite.api;

public class Reader
{
  public Object getRawInfo(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world, String info)
  {
    return com.miraclem4n.mchat.api.Reader.getRawInfo(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world, info);
  }

  public Object getRawPrefix(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    return com.miraclem4n.mchat.api.Reader.getRawPrefix(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public Object getRawSuffix(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    return com.miraclem4n.mchat.api.Reader.getRawSuffix(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public Object getRawGroup(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    return com.miraclem4n.mchat.api.Reader.getRawGroup(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public String getInfo(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world, String info) {
    return com.miraclem4n.mchat.api.Reader.getInfo(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world, info);
  }

  public String getPrefix(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    return com.miraclem4n.mchat.api.Reader.getPrefix(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public String getSuffix(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    return com.miraclem4n.mchat.api.Reader.getSuffix(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public String getGroup(String name, String world) {
    return com.miraclem4n.mchat.api.Reader.getGroup(name, world);
  }

  public String getGroupName(String group) {
    return com.miraclem4n.mchat.api.Reader.getGroupName(group);
  }

  public String getWorldName(String world) {
    return com.miraclem4n.mchat.api.Reader.getWorldName(world);
  }

  public String getMName(String name) {
    return com.miraclem4n.mchat.api.Reader.getMName(name);
  }

  public String getEventMessage(in.mDev.MiracleM4n.mChatSuite.types.EventType type) {
    return com.miraclem4n.mchat.api.Reader.getEventMessage(com.miraclem4n.mchat.types.EventType.fromName(type.getName()));
  }
}