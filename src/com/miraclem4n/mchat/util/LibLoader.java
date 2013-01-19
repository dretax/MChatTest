package com.miraclem4n.mchat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class LibLoader
{
  static URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();

  public static Boolean include(String filename, String url) {
    if (!download(filename, url).booleanValue()) {
      return Boolean.valueOf(false);
    }
    File file = getFile(filename);

    return load(file);
  }

  public static Boolean download(String filename, String url) {
    File file = getFile(filename);

    if (!file.exists()) {
      MessageUtil.log("Downloading library " + filename);

      if (!downloadUrl(url, file).booleanValue()) {
        MessageUtil.log("Failed to download " + filename);
        return Boolean.valueOf(false);
      }
    }

    return Boolean.valueOf(true);
  }

  private static File getFile(String filename) {
    Boolean dirCreated = Boolean.valueOf(false);

    if (!new File("./lib").exists()) {
      dirCreated = Boolean.valueOf(new File("./lib").mkdirs());
    }
    if (dirCreated.booleanValue()) {
      MessageUtil.log("Lib Directory Created.");
    }
    return new File("./lib/" + filename);
  }

  private static Boolean downloadUrl(String url, File file) {
    try {
      URL urlz = new URL(url);
      ReadableByteChannel ch = Channels.newChannel(urlz.openStream());
      FileOutputStream fos = new FileOutputStream(file);
      fos.getChannel().transferFrom(ch, 0L, 16777216L);

      return Boolean.valueOf(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Boolean.valueOf(false);
  }

  private static Boolean load(File file)
  {
    try {
      return load(file.toURI().toURL()); } catch (MalformedURLException e) {
    }
    return Boolean.valueOf(false);
  }

  private static Boolean load(URL url)
  {
    for (URL otherUrl : loader.getURLs())
      if (otherUrl.sameFile(url))
        return Boolean.valueOf(true);
    try
    {
      Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
      addURLMethod.setAccessible(true);
      addURLMethod.invoke(loader, new Object[] { url });
    } catch (Exception e) {
      return Boolean.valueOf(false);
    }

    return Boolean.valueOf(true);
  }
}