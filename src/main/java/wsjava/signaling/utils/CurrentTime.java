package wsjava.signaling.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {
  public static String get() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    return dtf.format(now);
  }
}
