package wsjava.signaling.types;

import java.util.ArrayList;

class Data<T> {
  T data;
}

public class MessageObject {
  public DataType dataType;
  public ArrayList<String> listData;
  public String stringData;
  public String log;
  public String timeStamp;

  public MessageObject() {}

  public MessageObject(DataType dataType, String stringData, String log, String timeStamp) {
    this.dataType = dataType;
    this.stringData = stringData;
    this.log = log;
    this.timeStamp = timeStamp;
  }

  public MessageObject(DataType dataType, ArrayList<String> listData, String log, String timeStamp) {
    this.dataType = dataType;
    this.listData = listData;
    this.log = log;
    this.timeStamp = timeStamp;
  }
}
