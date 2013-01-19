package in.mDev.MiracleM4n.mChatSuite.api;

public class Writer
{
  public void addBase(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type)
  {
    com.miraclem4n.mchat.api.Writer.addBase(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()));
  }

  public void addBase(String player, String group) {
    com.miraclem4n.mchat.api.Writer.addBase(player, group);
  }

  public void addWorld(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    com.miraclem4n.mchat.api.Writer.addWorld(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public void setInfoVar(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String var, Object value) {
    com.miraclem4n.mchat.api.Writer.setInfoVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), var, value);
  }

  public void setWorldVar(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world, String var, Object value) {
    com.miraclem4n.mchat.api.Writer.setWorldVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world, var, value);
  }

  public void setGroup(String player, String group) {
    com.miraclem4n.mchat.api.Writer.setGroup(player, group);
  }

  public void removeBase(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type) {
    com.miraclem4n.mchat.api.Writer.removeBase(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()));
  }

  public void removeInfoVar(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String var) {
    com.miraclem4n.mchat.api.Writer.removeInfoVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), var);
  }

  public void removeWorld(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world) {
    com.miraclem4n.mchat.api.Writer.removeWorld(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
  }

  public void removeWorldVar(String name, in.mDev.MiracleM4n.mChatSuite.types.InfoType type, String world, String var) {
    com.miraclem4n.mchat.api.Writer.removeWorldVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world, var);
  }
}