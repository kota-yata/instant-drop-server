package wsjava.signaling.types;

import java.util.ArrayList;

import javax.xml.bind.TypeConstraintException;

class Data<T> {
  T data;
}

enum DataType {
  String, List;
  public static DataType toDataType(String subjectString) {
    for (DataType dataType : values()) {
      if (dataType.name().equals(subjectString)) return dataType;
    }
    throw new TypeConstraintException("The string is incompatible to DataType");
  }
}

public class MessageObject {
  public DataType dataType;
  public ArrayList<String> listData;
  public String stringData;
  public String log;

  public MessageObject() {}

  public MessageObject(String stringData, String log) {
    this.dataType = DataType.String;
    this.stringData = stringData;
    this.log = log;
  }

  public MessageObject(ArrayList<String> listData, String log) {
    this.dataType = DataType.List;
    this.listData = listData;
    this.log = log;
  }
}
