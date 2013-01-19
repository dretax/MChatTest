package com.miraclem4n.mchat.util;

import java.util.Date;

public class TimerUtil
{
  long start = 0L;
  long end = 0L;

  public TimerUtil() {
    this.start = new Date().getTime();
  }

  public void reset() {
    this.start = 0L;
    this.end = 0L;
  }

  public void start() {
    this.start = new Date().getTime();
  }

  public void stop() {
    this.end = new Date().getTime();
  }

  public long difference() {
    return this.end - this.start;
  }
}