package wsjava.signaling.utils;

import java.util.Random;

public class FacePicker {
  static String[] choices = {"😀", "😃", "😄", "😆", "🙂", "🙃", "😊", "😇", "😮", "😗", "🥲", "😋", "🤑", "🤗", "🤭", "🤔", "🤐", "🤨", "😐", "😑", "😶", "🤥", "😎", "🤓", "🧐", "😕", "😦"};
  public static String get() {
    Random rand = new Random();
    int num = rand.nextInt(0, choices.length);
    return choices[num];
  }
}
