package in.mDev.MiracleM4n.mChatSuite;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.metrics.Metrics;
import in.mDev.MiracleM4n.mChatSuite.api.API;
import in.mDev.MiracleM4n.mChatSuite.api.Parser;
import in.mDev.MiracleM4n.mChatSuite.api.Reader;
import in.mDev.MiracleM4n.mChatSuite.api.Writer;
import java.io.IOException;

public class mChatSuite extends MChat
{
  API api;
  Parser parser;
  Writer writer;
  Reader reader;

  public void onEnable()
  {
    try
    {
      this.metrics = new Metrics(this);
      this.metrics.start();
    } catch (IOException ignored) {
    }
    initializeClasses();

    super.onEnable();
  }

  public void onDisable() {
    super.onDisable();
  }

  void initializeClasses() {
    this.api = new API();
    this.parser = new Parser();
    this.reader = new Reader();
    this.writer = new Writer();
  }

  @Deprecated
  public API getAPI()
  {
    return this.api;
  }

  @Deprecated
  public Parser getParser()
  {
    return this.parser;
  }

  @Deprecated
  public Reader getReader()
  {
    return this.reader;
  }

  @Deprecated
  public Writer getWriter()
  {
    return this.writer;
  }
}