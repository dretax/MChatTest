package in.mDev.MiracleM4n.mChatSuite.types;

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
}