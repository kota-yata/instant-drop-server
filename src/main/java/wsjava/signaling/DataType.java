package wsjava.signaling;

import javax.xml.bind.TypeConstraintException;

public enum DataType {
  LocalId, Peers, Offer, Answer, IceCandidate, Error;
  public static DataType toDataType(String subjectString) {
    for (DataType dataType : values()) {
      if (dataType.name().equals(subjectString)) return dataType;
    }
    throw new TypeConstraintException("The string is incompatible to DataType");
  }
}
